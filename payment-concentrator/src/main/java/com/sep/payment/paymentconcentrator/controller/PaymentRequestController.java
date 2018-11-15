package com.sep.payment.paymentconcentrator.controller;


import com.sep.payment.paymentconcentrator.domain.dto.PaymentDataDTO;
import com.sep.payment.paymentconcentrator.domain.dto.RequestDTO;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/pc")
public class PaymentRequestController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Autowired
    private RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(value = "/pay-by-bank-card")
    public ResponseEntity<PaymentDataDTO> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) {
        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(requestDTO.getClientId(), requestDTO.getAmount());

        return ResponseEntity.ok(restTemplate.postForObject("http://localhost:8762/bank/get-payment-url",
                paymentRequest, PaymentDataDTO.class));
    }
}
