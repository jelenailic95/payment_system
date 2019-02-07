package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.dto.CardAmountDTO;
import com.sep.bank.bankservice.entity.dto.PaymentDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentRequestDTO;
import com.sep.bank.bankservice.entity.dto.TransactionDTO;
import com.sep.bank.bankservice.service.BankService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AcquirerController {

    private final BankService bankService;

    private final ModelMapper modelMapper = new ModelMapper();

    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(AcquirerController.class);

    @Autowired
    public AcquirerController(BankService bankService, RestTemplate restTemplate) {
        this.bankService = bankService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/get-payment-url")
    public ResponseEntity<PaymentDataDTO> getPaymentUrl(@RequestBody PaymentRequestDTO request) {
        logger.info("Request - get payment url. Merchant: {}", request.getMerchantId());
        PaymentDataDTO paymentData = bankService.getPaymentUrl(request);

        logger.info("Payment url is successfully generated: {}", paymentData.getPaymentUrl());
        return ResponseEntity.ok(paymentData);
    }

    @PostMapping("/pay-by-card")
    public ResponseEntity<TransactionDTO> payByCard(@RequestBody CardAmountDTO cardAmountDTO) {
        logger.info("Request - pay by bank card. Amount: {}", cardAmountDTO.getAmount());
        Transaction transaction = bankService.checkBankForCard(cardAmountDTO);

        logger.info("Transaction is done. Transaction status: {}", transaction.getStatus());
        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        // final step - send transaction information to the payment concentrator
        logger.info("Transaction object is forwarded to the payment concentrator.");

        TransactionDTO finalUrl = restTemplate.postForObject("https://localhost:8443/pc/finish-transaction", transactionDTO,
                TransactionDTO.class);

        return ResponseEntity.ok(finalUrl);
    }
}
