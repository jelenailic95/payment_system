package com.sep.payment.paymentconcentrator.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sep.payment.paymentconcentrator.domain.dto.PaymentDataDTO;
import com.sep.payment.paymentconcentrator.domain.dto.RequestDTO;
import com.sep.payment.paymentconcentrator.domain.dto.ResponseOrderDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.Constants;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

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

    private Logger logger = LoggerFactory.getLogger(PaymentRequestController.class);

    @PostMapping(value = "/pay-by-bank-card")
    public ResponseEntity<?> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bank card.");
        String client = Utility.readToken(requestDTO.getClient());
        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(client, requestDTO.getAmount(), requestDTO.getClientId());

        logger.info("Request - call endpoint(from the bank): get payment url.");

        PaymentDataDTO paymentDataDTO = Objects.requireNonNull(restTemplate.postForObject("http://localhost:8762/" +
                requestDTO.getClientId() + "-service/get-payment-url",
                paymentRequest, PaymentDataDTO.class));

//        return new RedirectView("http://localhost:4200/pay-by-card/123");
//        return new
//        return ResponseEntity.ok();

//        HttpHeaders headers = new HttpHeaders();
//        UriComponentsBuilder b = UriComponentsBuilder.fromPath("http://localhost:4200/pay-by-card/123");
//
//        UriComponents uriComponents =  b.build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/pay-by-card/123");


        return new ResponseEntity<>(headers,HttpStatus.FOUND);
//        ModelMap model = new ModelMap();
//        model.addAttribute("attribute", "forwardWithForwardPrefix");
//        return new  ModelAndView("forward:/http://localhost:4200/pay-by-card/123", model);
    }

    @GetMapping(value = "/pay-by-card/123")
    public ResponseEntity a() throws UnsupportedEncodingException {
        return null;
    }

        @PostMapping(value = "/pay-by-bitcoin")
    public ResponseEntity<ResponseOrderDTO> payWithBitcoin(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bitcoin.");

        String client = Utility.readToken(requestDTO.getClient());
        Client foundClient = clientService.findByClientMethod(client, "crypto");
        RequestDTO dto = new RequestDTO(client, foundClient.getClientId(), requestDTO.getAmount());

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity("http://localhost:8762/crypto-service/bitcoin-payment", dto, ResponseOrderDTO.class);
        return ResponseEntity.ok(Objects.requireNonNull(o.getBody()));
    }

    @GetMapping(value = "/get-token")
    public ResponseEntity<String> getToken() throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
        String token = JWT.create()
                .withClaim("client", "Laguna")
                .sign(algorithm);
        return ResponseEntity.ok(token);
    }


}
