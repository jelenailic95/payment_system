package com.sep.payment.paymentconcentrator.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sep.payment.paymentconcentrator.domain.dto.FinishPaymentDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

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
    public ResponseEntity<PaymentDataDTO> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bank card.");
        String client = Utility.readToken(requestDTO.getClient());
        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(client, requestDTO.getAmount(), requestDTO.getClientId());

        logger.info("Request - call endpoint(from the bank): get payment url.");

        PaymentDataDTO paymentDataDTO = Objects.requireNonNull(restTemplate.postForObject("https://localhost:8762/" +
                        requestDTO.getClientId() + "-service/get-payment-url",
                paymentRequest, PaymentDataDTO.class));

        return ResponseEntity.ok().body(paymentDataDTO);
    }


    @PostMapping(value = "/pay-by-bitcoin")
    public ResponseEntity<ResponseOrderDTO> payWithBitcoin(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bitcoin.");

        String client = Utility.readToken(requestDTO.getClient());
        Client foundClient = clientService.findByClientMethod(client, "crypto");
        RequestDTO dto = new RequestDTO(client, foundClient.getClientId(), requestDTO.getAmount());

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity("https://localhost:8762/crypto-service/bitcoin-payment", dto, ResponseOrderDTO.class);
        return ResponseEntity.ok(Objects.requireNonNull(o.getBody()));
    }


    @PostMapping(value = "/pay-by-paypal")
    public ResponseEntity payWithPayPal(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by paypal.");
        String client = Utility.readToken(requestDTO.getClient());
        Client foundClient = clientService.findByClientMethod(client, "paypal");
        RequestDTO dto = new RequestDTO(client, foundClient.getClientId(), requestDTO.getAmount());
        dto.setClientSecret(foundClient.getClientPassword());
        String url = restTemplate.postForEntity("https://localhost:8762/paypal-service/pay", dto, String.class).getBody();
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping(value = "/finish-payment/{token}")
    public ResponseEntity finishPaymentWithPaypal(@RequestBody FinishPaymentDTO finishPaymentDTO, @PathVariable String token) {
        logger.info("Finishing payment - pay paypal.");
        boolean success = restTemplate.getForEntity(("https://localhost:8762/paypal-service/" +
                "pay/success?id=").concat(finishPaymentDTO.getId())
                .concat("&secret=").concat(finishPaymentDTO.getSecret())
                .concat("&paymentId=").concat(finishPaymentDTO.getPaymentId())
                .concat("&PayerID=").concat(finishPaymentDTO.getPayerId()), Boolean.class).getBody();
        return new ResponseEntity<>(success, HttpStatus.OK);


    }


}
