package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.dto.CardAmountDTO;
import com.sep.bank.bankservice.entity.dto.PaymentDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentRequestDTO;
import com.sep.bank.bankservice.entity.dto.TransactionDTO;
import com.sep.bank.bankservice.repository.CardRepository;
import com.sep.bank.bankservice.service.BankService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
public class AcquirerController {

    @Value("${p-concentrator.host}")
    private String pConcentratorHost;

    @Autowired
    private BankService bankService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(AcquirerController.class);

    @Autowired
    public AcquirerController(BankService bankService, RestTemplate restTemplate) {
        this.bankService = bankService;
        this.restTemplate = restTemplate;
    }

    /**
     * Get payment url.
     *
     * @param request payment request data.
     * @return payment request object with the created payment url.
     */
    @PostMapping("/get-payment-url")
    public ResponseEntity<PaymentDataDTO> getPaymentUrl(@RequestBody PaymentRequestDTO request) {
        logger.info("Request - get payment url. Merchant: {}", request.getMerchantId());
        PaymentDataDTO paymentData = bankService.getPaymentUrl(request);

        logger.info("Payment url is successfully generated: {}", paymentData.getPaymentUrl());
        return ResponseEntity.ok(paymentData);
    }

    /**
     * POST: Pay buy bank card.
     *
     * @param cardAmountDTO amount and card data.
     * @return transaction result
     */
    @PostMapping("/pay-by-card")
    public ResponseEntity<TransactionDTO> payByCard(@RequestBody CardAmountDTO cardAmountDTO) {
        logger.info("Request - pay by bank card. Amount: {}", cardAmountDTO.getAmount());
        Transaction transaction = bankService.checkBankForCard(cardAmountDTO);

        logger.info("Transaction is done. Transaction status: {}", transaction.getStatus());
        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        // final step - send transaction information to the payment concentrator
        logger.info("Transaction object is forwarded to the payment concentrator.");

        TransactionDTO finalUrl = restTemplate.postForObject(pConcentratorHost + "/pc/finish-transaction",
                transactionDTO, TransactionDTO.class);

        return ResponseEntity.ok(finalUrl);
    }
}
