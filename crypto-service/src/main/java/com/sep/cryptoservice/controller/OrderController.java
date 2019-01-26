package com.sep.cryptoservice.controller;

import com.sep.cryptoservice.domain.Order;
import com.sep.cryptoservice.domain.dto.RequestDTO;
import com.sep.cryptoservice.domain.dto.ResponseOrderDTO;
import com.sep.cryptoservice.security.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
        o.getBody().setClientId(requestDTO.getClientId());
        return o;
    }


}

