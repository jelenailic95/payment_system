package com.sep.cryptoservice.controller;

import com.sep.cryptoservice.domain.GetOrderTask;
import com.sep.cryptoservice.domain.Order;
import com.sep.cryptoservice.domain.dto.RequestDTO;
import com.sep.cryptoservice.domain.dto.ResponseOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    private final TaskScheduler scheduler = new ConcurrentTaskScheduler();

    @PostMapping("/bitcoin-payment")
    public ResponseEntity<ResponseOrderDTO> createOrder(@RequestBody RequestDTO requestDTO) {
        Order order = new Order(requestDTO.getAmount(), "BTC", "BTC", "http://ex.com");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + requestDTO.getClientId());
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders",
                entity, ResponseOrderDTO.class);
        System.out.println(o.getBody().getPayment_url());
        check(o.getBody().getId(), requestDTO.getClientId());

        return o;
    }

//    @GetMapping("/test")
//    public String test() {
//        this.check("140351", requestDTO.getClientId());
//        System.out.println("TEST");
//        return "prosao";
//    }

    public void check(String orderId, String clientId) {
        ScheduledFuture<?> d = scheduler.schedule(new GetOrderTask(restTemplate, orderId, clientId), new CronTrigger("*/5 * * * * *"));

    }
}

