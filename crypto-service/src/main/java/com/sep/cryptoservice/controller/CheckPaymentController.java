package com.sep.cryptoservice.controller;

import com.sep.cryptoservice.domain.dto.ResponseOrderDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

@RestController
public class CheckPaymentController {


    private final RestTemplate restTemplate;

    CheckPaymentController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @GetMapping("/check-payment/{clientId}/{orderId}")
    public ResponseEntity<ResponseOrderDTO> createOrder(@PathVariable String clientId, @PathVariable String orderId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + clientId);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<ResponseOrderDTO> o = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/" + orderId, HttpMethod.GET,
                entity, ResponseOrderDTO.class);

        return o;
    }


}
