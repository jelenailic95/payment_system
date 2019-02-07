package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.FinishPaymentDTO;
import com.sep.payment.paymentconcentrator.domain.dto.PlanInfoDTO;
import com.sep.payment.paymentconcentrator.domain.dto.RequestCreatePlan;
import com.sep.payment.paymentconcentrator.domain.dto.SubscribeDto;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping(value = "/pc/subscription")
public class SubscriptionController {

    @Value("${proxy.host}")
    private String proxyHost;


    private final RestTemplate restTemplate;
    private final ClientService clientService;

    private Logger logger = LoggerFactory.getLogger(SubscriptionController.class);


    @Autowired
    public SubscriptionController(RestTemplate restTemplate, ClientService clientService) {
        this.restTemplate = restTemplate;
        this.clientService = clientService;
    }


    @GetMapping("plan/{journal}/{method}")
    public ResponseEntity getSubscriptionPlanForJournal(@PathVariable String journal, @PathVariable String method) throws UnsupportedEncodingException {
        String token = Utility.readToken(journal);
        journal = token.split("-")[2];
        Client client = this.clientService.findByClientMethod(journal, method);
        return this.restTemplate.getForEntity(proxyHost + "/paypal-service/get-plan"
                .concat("?name=").concat(journal)
                .concat("&clientId=").concat(client.getClientId())
                .concat("&secret=").concat(client.getClientPassword()), List.class);
    }

    @PostMapping("plan/subscribe")
    public ResponseEntity subscribeToPlan(@RequestBody SubscribeDto dto) {
        Client client = this.clientService.findByClientMethod(dto.getNameOfJournal(), "paypal");
        SubscribeDto subscribeDto = SubscribeDto.builder()
                .clientId(client.getClientId())
                .secret(client.getClientPassword())
                .nameOfJournal(dto.getNameOfJournal()).planId(dto.getPlanId()).build();
        return this.restTemplate.postForEntity(proxyHost + "/paypal-service/plan/subscribe", subscribeDto, URL.class);
    }

    @PostMapping("plan")
    public ResponseEntity createNewPlan(@RequestBody PlanInfoDTO planInfoDTO) {
        Client client = this.clientService.findByClientMethod(planInfoDTO.getName(), "paypal");
        RequestCreatePlan plan = RequestCreatePlan.builder()
                .clientId(client.getClientId())
                .clientSecret(client.getClientPassword())
                .currency(planInfoDTO.getCurrency())
                .price(Double.parseDouble(planInfoDTO.getAmount()))
                .description(planInfoDTO.getDescription())
                .typeOfPlan("REGULAR")
                .cycles(Integer.parseInt(planInfoDTO.getCycles()))
                .frequencyInterval(Integer.parseInt(planInfoDTO.getFrequencyInterval()))
                .frequencyPayment(planInfoDTO.getFrequency())
                .nameOfJournal(planInfoDTO.getName())
                .build();
        return this.restTemplate.postForEntity(proxyHost + "/paypal-service/plan/create-plan", plan, String.class);

    }

    @PostMapping(value = "/plan/subscribe/finish/{token}")
    public ResponseEntity finishPaymentWithPaypal(@RequestBody SubscribeDto dto, @PathVariable String token) {
        logger.info("Finishing payment - pay paypal.");
        String success = restTemplate.getForEntity((proxyHost + "/paypal-service/" +
                "plan/finish-subscription?id=").concat(dto.getClientId())
                .concat("&secret=").concat(dto.getSecret())
                .concat("&planId=").concat(dto.getPlanId())
                .concat("&token=").concat(token), String.class).getBody();
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
