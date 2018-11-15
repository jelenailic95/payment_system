package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.RequestDTO;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.domain.dto.ClientDTO;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentMethodDTO;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping(value = "/pc")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(value = "/payment-methods")
    public ResponseEntity<Set<PaymentMethodDTO>> checkPaymentMethods(@RequestBody @Valid ClientDTO clientDTO) {
        Long cliendId = clientDTO.getId();
        Set<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethods(cliendId);
        Set<PaymentMethodDTO> paymentMethodDTOS = new HashSet<>();
        paymentMethods.forEach(paymentMethod -> paymentMethodDTOS.add(modelMapper.map(paymentMethod, PaymentMethodDTO.class)));
        return ResponseEntity.ok().body(paymentMethodDTOS);
    }

    @PostMapping(value = "/pay-by-bank-card")
    public String createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) {
        PaymentRequest paymentRequest = paymentMethodService.createPaymentRequest(requestDTO.getClientId(), requestDTO.getAmount());

        return restTemplate.postForObject("http://localhost:8762/bank/get-payment-url",
                paymentRequest, String.class);
    }
}
