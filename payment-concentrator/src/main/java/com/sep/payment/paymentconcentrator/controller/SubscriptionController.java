package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.PlanInfoDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/pc/subscription")
public class SubscriptionController {

    private final String SUBSCRIPTION_URL = "https://localhost:8762/paypal-service/get-plan";
    private final RestTemplate restTemplate;
    private final ClientService clientService;

    @Autowired
    public SubscriptionController(RestTemplate restTemplate, ClientService clientService) {
        this.restTemplate = restTemplate;
        this.clientService = clientService;
    }


    @GetMapping("plan/{journal}/{method}")
    public ResponseEntity getSubscriptionPlanForJournal(@PathVariable String journal, @PathVariable String method) {
        Client client = this.clientService.findByClientMethod(journal, method);
        return this.restTemplate.getForEntity(SUBSCRIPTION_URL
                .concat("?name=").concat(journal)
                .concat("&clientId=").concat(client.getClientId())
                .concat("&secret=").concat(client.getClientPassword()), PlanInfoDTO.class);
    }
}
