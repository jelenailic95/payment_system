package com.sep.cryptoservice.controller;

import com.sep.cryptoservice.domain.GetOrderTask;
import com.sep.cryptoservice.domain.Order;
import com.sep.cryptoservice.domain.ResponseOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    private final TaskScheduler scheduler = new ConcurrentTaskScheduler();

    @PostMapping("/bitcoin-payment")
    public ResponseEntity<ResponseOrderDTO> createOrder() {
        Order order = new Order(0.5, "BTC", "BTC",  "http://ex.com");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "kcepNwZbZD4bQegiCmuwyAg4RKzrDPn1z1V4LkGy");
        HttpEntity<Order> entity = new HttpEntity<>(order,headers);

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders",entity, ResponseOrderDTO.class);
        System.out.println(o.getBody().getPayment_url());
        check(o.getBody().getOrder_id());

        return o;
    }

    @GetMapping("/test")
    public String test() {
        this.check("140351");
        System.out.println("TEST");
        return "prosao";
    }

    public void check(String orderId){
        ScheduledFuture<?> d = scheduler.schedule(new GetOrderTask(restTemplate,orderId), new CronTrigger("*/5 * * * * *"));

    }
}

