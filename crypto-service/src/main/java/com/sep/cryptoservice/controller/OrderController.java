package com.sep.cryptoservice.controller;

import com.sep.cryptoservice.domain.Order;
import com.sep.cryptoservice.domain.dto.*;
import com.sep.cryptoservice.repository.OrderRepository;
import com.sep.cryptoservice.security.AES;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

@RestController
public class OrderController {

    private final RestTemplate restTemplate;
    private final TaskScheduler scheduler = new ConcurrentTaskScheduler();
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final AES aes;

    @Value("${pc.host}")
    private String pcHost;

    @Value("${pc-client.host}")
    private String pcClientHost;


    @Autowired
    public OrderController(RestTemplate restTemplate, OrderRepository orderRepository, AES aes) {
        this.restTemplate = restTemplate;
        this.aes = aes;
        this.modelMapper = new ModelMapper();
        this.orderRepository = orderRepository;
    }

    @PostMapping("/bitcoin-payment")
    public ResponseEntity<ResponseOrderDTO> createOrder(@RequestBody BitcoinPaymentDto requestDTO) {
        Order order = new Order(requestDTO.getRequestDTO().getAmount(), "USD", "USD",
                pcClientHost + "result/success?username=" + requestDTO.getPaymentRequest().getUsername(),
                pcClientHost + "result/cancel?username=");
        HttpHeaders headers = new HttpHeaders();
        String decrToken = aes.decrypt(requestDTO.getRequestDTO().getClientId());
        headers.add("Authorization", "Bearer " + decrToken);
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders",
                entity, ResponseOrderDTO.class);
        Order newOrder = modelMapper.map(o.getBody(), Order.class);
        orderRepository.save(newOrder);
        o.getBody().setClientId(requestDTO.getRequestDTO().getClientId());

        check(o.getBody().getId(), decrToken, requestDTO.getPaymentRequest());
        return o;
    }

    public void check(String orderId, String clientId, PaymentRequest p) {
//        ScheduledFuture<?> d = scheduler.schedule(new GetOrderTask(restTemplate, orderId, clientId), new CronTrigger("*/5 * * * * *"));
        final Timer timer = new Timer();

        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + clientId);
                HttpEntity<String> entity = new HttpEntity<>("", headers);
                ResponseEntity<ResponseOrderDTO> o = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/" + orderId, HttpMethod.GET,
                        entity, ResponseOrderDTO.class);

                System.out.println(o.getBody().getStatus());
                if (o.getBody().getStatus().equals("paid")) {
                    logger.info("Order successfully paid.");

//                    FinishResponseDto finishPaymentDTO = FinishResponseDto.builder().typeOfPayment(p.getTypeOfPayment()).
//                            journalName(p.getJournalName()).paperId(p.getPaperId()).username(p.getUsername()).scName(p.getScName()).build();
                    timer.cancel();
                    timer.purge();
                    restTemplate.postForEntity(pcHost + "pc/successful-transaction/" + p.getId().toString(),
                            null, String.class);
                }
                if (o.getBody().getStatus().equals("invalid") || o.getBody().getStatus().equals("canceled")) {
                    logger.info("Order is canceled.");

                    timer.cancel();
                    timer.purge();
                }
            }
        };

        timer.schedule(task, 1000, 5000);

    }


}

