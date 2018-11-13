package com.sep.payment.paymentconcentrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/concentrator")
public class PaymentConcentratorController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test")
    public String findAll() {
        return restTemplate.getForObject("http://localhost:8762/bank/getAll", String.class);
    }
}

