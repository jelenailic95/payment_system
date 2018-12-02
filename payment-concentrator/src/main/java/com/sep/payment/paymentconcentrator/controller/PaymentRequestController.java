package com.sep.payment.paymentconcentrator.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentDataDTO;
import com.sep.payment.paymentconcentrator.domain.dto.RequestDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Constants;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/pc")
public class PaymentRequestController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(value = "/pay-by-bank-card")
    public ResponseEntity<PaymentDataDTO> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {

        String client = Utility.readToken(requestDTO.getClient());
        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(client, requestDTO.getAmount(), requestDTO.getBankName());
        System.out.println(client);
        return ResponseEntity.ok(restTemplate.postForObject("http://localhost:8762/"+requestDTO.getBankName()+"/get-payment-url",
                paymentRequest, PaymentDataDTO.class));
    }

    @GetMapping(value = "/get-token")
    @PreAuthorize("#oauth2.hasScope('custom_mod')")
    public ResponseEntity<String> createPaymentRequest() throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
        String token = JWT.create()
                .withClaim("client", "Laguna")
                .sign(algorithm);
        return ResponseEntity.ok(token);
    }


}
