package com.sep.payment.paymentconcentrator.controller;


import com.sep.payment.paymentconcentrator.domain.dto.FinishPaymentDTO;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentDataDTO;
import com.sep.payment.paymentconcentrator.domain.dto.RequestDTO;
import com.sep.payment.paymentconcentrator.domain.dto.ResponseOrderDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping(value = "/pc")
public class PaymentRequestController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();

    private Logger logger = LoggerFactory.getLogger(PaymentRequestController.class);

    @PostMapping(value = "/pay-by-bank-card")
    public ResponseEntity<PaymentDataDTO> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bank card.");
        String token = Utility.readToken(requestDTO.getClient());
        String client = token.split("-")[2];
        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(client, requestDTO.getAmount(), requestDTO.getClientId());

        logger.info("Request - call endpoint(from the bank): get payment url.");

        PaymentDataDTO paymentDataDTO = Objects.requireNonNull(restTemplate.postForObject("https://localhost:8762/" +
                        requestDTO.getClientId() + "-service/get-payment-url", paymentRequest, PaymentDataDTO.class));

        return ResponseEntity.ok().body(paymentDataDTO);
    }


    @PostMapping(value = "/pay-by-bitcoin")
    public ResponseEntity<ResponseOrderDTO> payWithBitcoin(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bitcoin.");
        // localStorage.getItem('user') + '-journal' + '-' + journal.name + '-' + journal.price
        String token = Utility.readToken(requestDTO.getClient());
        String[] tokens = token.split("-");

        Client foundClient = clientService.findByClientMethod(tokens[2], "crypto");
        RequestDTO dto = new RequestDTO(tokens[2], foundClient.getClientId(), Double.parseDouble(tokens[3]));
        if (tokens[1].equals("journal"))
            paymentRequestService.createRequest(tokens[0], Double.parseDouble(tokens[3]), tokens[2], null, tokens[1]);
        else
            paymentRequestService.createRequest(tokens[0], Double.parseDouble(tokens[3]), null, Long.parseLong(tokens[2]),tokens[1]);

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity("https://localhost:8762/crypto-service/bitcoin-payment", dto, ResponseOrderDTO.class);
//        check(o.getBody().getId(), requestDTO.getClientId());

        return ResponseEntity.ok(Objects.requireNonNull(o.getBody()));
    }

    public void check(String orderId, String clientId) {
//        ScheduledFuture<?> d = scheduler.schedule(new GetOrderTask(restTemplate, orderId, clientId), new CronTrigger("*/5 * * * * *"));
        final Timer timer = new Timer();

        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ResponseEntity<ResponseOrderDTO> o = restTemplate.getForEntity("https://localhost:8762/check-payment/".concat(clientId).concat("/")
                        .concat(orderId), ResponseOrderDTO.class);

                if (o.getBody().getStatus().equals("new")) {
                    System.out.println(o.getBody().getStatus());
                    System.out.println("skontao sam");
                    timer.cancel();
                    timer.purge();
                }
            }
        };

        timer.schedule(task, 5000);

    }


    @PostMapping(value = "/pay-by-paypal")
    public ResponseEntity payWithPayPal(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by paypal.");
        String client = Utility.readToken(requestDTO.getClient());
        Client foundClient = clientService.findByClientMethod(client, "paypal");
        RequestDTO dto = new RequestDTO(client, foundClient.getClientId(), requestDTO.getAmount());
        dto.setClientSecret(foundClient.getClientPassword());
        String url = restTemplate.postForEntity("https://localhost:8762/paypal-service/pay", dto, String.class).getBody();
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping(value = "/finish-payment/{token}")
    public ResponseEntity finishPaymentWithPaypal(@RequestBody FinishPaymentDTO finishPaymentDTO, @PathVariable String token) {
        logger.info("Finishing payment - pay paypal.");
        boolean success = restTemplate.getForEntity(("https://localhost:8762/paypal-service/" +
                "pay/success?id=").concat(finishPaymentDTO.getId())
                .concat("&secret=").concat(finishPaymentDTO.getSecret())
                .concat("&paymentId=").concat(finishPaymentDTO.getPaymentId())
                .concat("&PayerID=").concat(finishPaymentDTO.getPayerId()), Boolean.class).getBody();
        return new ResponseEntity<>(success, HttpStatus.OK);


    }


}
