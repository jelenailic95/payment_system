package com.sep.cryptoservice.controller;

import com.sep.cryptoservice.domain.Order;
import com.sep.cryptoservice.domain.dto.RequestDTO;
import com.sep.cryptoservice.domain.dto.ResponseOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    private final TaskScheduler scheduler = new ConcurrentTaskScheduler();

    @PostMapping("/bitcoin-payment")
    public ResponseEntity<ResponseOrderDTO> createOrder(@RequestBody RequestDTO requestDTO) {
        Order order = new Order(requestDTO.getAmount(), "USD", "USD", "http://ex.com");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + requestDTO.getClientId());
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders",
                entity, ResponseOrderDTO.class);
        o.getBody().setClientId(requestDTO.getClientId());
        return o;
    }


}

