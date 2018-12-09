package com.sep.cryptoservice.domain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.TimerTask;

public class GetOrderTask extends TimerTask {


    private RestTemplate restTemplate;
    private String orderId = "";

    public GetOrderTask(RestTemplate restTemplate, String order){
        this.restTemplate = restTemplate;
        this.orderId = order;
    }
    public GetOrderTask(String order) {
        orderId = order;
    }


    @Override
    public void run() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "kcepNwZbZD4bQegiCmuwyAg4RKzrDPn1z1V4LkGy");
        HttpEntity<String> entity = new HttpEntity<>("",headers);

        ResponseEntity<ResponseOrderDTO> o = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/"+orderId, HttpMethod.GET,
                entity, ResponseOrderDTO.class);
        System.out.println(o.getBody().getPayment_url());
        System.out.println(o.getBody().getStatus());

    }
}
