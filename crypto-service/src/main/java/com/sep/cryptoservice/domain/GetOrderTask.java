package com.sep.cryptoservice.domain;

import com.sep.cryptoservice.domain.dto.ResponseOrderDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.TimerTask;

public class GetOrderTask extends TimerTask {


    private RestTemplate restTemplate;
    private String orderId;
    private String clientId;

    public GetOrderTask(RestTemplate restTemplate, String orderId, String clientId) {
        this.restTemplate = restTemplate;
        this.orderId = orderId;
        this.clientId = clientId;
    }

    public GetOrderTask(String order) {
        orderId = order;
    }


    @Override
    public void run() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + clientId);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<ResponseOrderDTO> o = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/" + orderId, HttpMethod.GET,
                entity, ResponseOrderDTO.class);
        System.out.println(o.getBody().getPayment_url());
        System.out.println(o.getBody().getStatus());

    }
}
