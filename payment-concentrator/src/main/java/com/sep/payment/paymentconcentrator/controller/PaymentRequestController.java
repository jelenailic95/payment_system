package com.sep.payment.paymentconcentrator.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentDataDTO;
import com.sep.payment.paymentconcentrator.domain.dto.RequestDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(value = "/pay-by-bank-card")
    public ResponseEntity<PaymentDataDTO> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//        String journal = Jwts.parser().setSigningKey("secret").parseClaimsJws(requestDTO.getToken()).getBody().getSubject();

        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(requestDTO.getToken());
        String journal = jwt.getClaim("journal").asString();

        Client client = clientService.findByJournal(journal);
        if(client == null){
            System.out.println("Nije dobar token");
            return null;
        }
        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(requestDTO.getClientId(), requestDTO.getAmount());

        return ResponseEntity.ok(restTemplate.postForObject("http://localhost:8762/bank/get-payment-url",
                paymentRequest, PaymentDataDTO.class));
    }

    @GetMapping(value = "/get-token")
    public ResponseEntity<String> createPaymentRequest() throws UnsupportedEncodingException {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String jws = Jwts.builder().setSubject("Laguna").signWith(SignatureAlgorithm.HS256,"secret").compact();
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        String token = JWT.create()
                .withClaim("journal", "Laguna")
                .sign(algorithm);
        return ResponseEntity.ok(token);
    }


}
