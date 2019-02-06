package com.sep.payment.paymentconcentrator.controller;


import com.sep.payment.paymentconcentrator.domain.dto.*;
import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.service.ClientService;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import com.sep.payment.paymentconcentrator.utility.Utility;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@RestController
@RequestMapping(value = "/pc")
public class PaymentRequestController {

    @Value("${proxy.host}")
    private String proxyHost;

    private final PaymentRequestService paymentRequestService;

    private final ClientService clientService;

    private final RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();

    private Logger logger = LoggerFactory.getLogger(PaymentRequestController.class);

    @Autowired
    public PaymentRequestController(PaymentRequestService paymentRequestService, ClientService clientService, RestTemplate restTemplate) {
        this.paymentRequestService = paymentRequestService;
        this.clientService = clientService;
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/pay-by-bank-card")
    public ResponseEntity<PaymentDataDTO> createPaymentRequest(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bank card.");
        String token = Utility.readToken(requestDTO.getClient());
        String client = token.split("-")[2];
        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(client, requestDTO.getAmount(), requestDTO.getClientId());

        logger.info("Request - call endpoint(from the bank): get payment url.");

        PaymentDataDTO paymentDataDTO = Objects.requireNonNull(restTemplate.postForObject(proxyHost + "/" +
                        requestDTO.getClientId() + "-service/get-payment-url",
                paymentRequest, PaymentDataDTO.class));

        return ResponseEntity.ok().body(paymentDataDTO);
    }


    @PostMapping(value = "/pay-by-bitcoin")
    public ResponseEntity<ResponseOrderDTO> payWithBitcoin(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by bitcoin.");
        // localStorage.getItem('user') + '-journal' + '-' + journal.name + '-' + journal.price
        String token = Utility.readToken(requestDTO.getClient());
        String[] tokens = token.split("-");

        Client foundClient = clientService.findByClientMethod(tokens[2], "crypto");
        RequestDTO dto = new RequestDTO(tokens[2], foundClient.getClientId(), Double.parseDouble(tokens[3]));
        PaymentRequest paymentRequest;

        if (tokens[1].equals("journal"))
            paymentRequest = paymentRequestService.createRequest(tokens[0], Double.parseDouble(tokens[3]),
                    tokens[2], null, tokens[1], tokens[4]);
        else
            paymentRequest = paymentRequestService.createRequest(tokens[0], Double.parseDouble(tokens[3]),
                    tokens[2], Long.parseLong(tokens[5]), tokens[1], tokens[4]);
        BitcoinPaymentDto paymentDto = BitcoinPaymentDto.builder().requestDTO(dto).paymentRequest(paymentRequest).build();

        ResponseEntity<ResponseOrderDTO> o = restTemplate.postForEntity(proxyHost + "/crypto-service/bitcoin-payment", paymentDto, ResponseOrderDTO.class);

        return ResponseEntity.ok(Objects.requireNonNull(o.getBody()));
    }


    @PostMapping(value = "/pay-by-paypal")
    public ResponseEntity payWithPayPal(@RequestBody @Valid RequestDTO requestDTO) throws UnsupportedEncodingException {
        logger.info("Request - pay by paypal.");
        String client = Utility.readToken(requestDTO.getClient());
        Client foundClient = clientService.findByClientMethod(client, "paypal");
        RequestDTO dto = new RequestDTO(client, foundClient.getClientId(), requestDTO.getAmount());
        dto.setClientSecret(foundClient.getClientPassword());
        String url = restTemplate.postForEntity(proxyHost + "/paypal-service/pay", dto, String.class).getBody();
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping(value = "/finish-payment/{token}")
    public ResponseEntity finishPaymentWithPaypal(@RequestBody FinishPaymentDTO finishPaymentDTO, @PathVariable String token) {
        logger.info("Finishing payment - pay paypal.");

        boolean success = restTemplate.getForEntity((proxyHost + "/paypal-service/" +
                "pay/success?id=").concat(finishPaymentDTO.getId())
                .concat("&secret=").concat(finishPaymentDTO.getSecret())
                .concat("&paymentId=").concat(finishPaymentDTO.getPaymentId())
                .concat("&PayerID=").concat(finishPaymentDTO.getPayerId()), Boolean.class).getBody();
        return new ResponseEntity<>(success, HttpStatus.OK);
    }


}
