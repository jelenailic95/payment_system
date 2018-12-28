package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.dto.*;
import com.sep.bank.bankservice.repository.CardRepository;
import com.sep.bank.bankservice.security.AES;
import com.sep.bank.bankservice.service.BankService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AcquirerController {

    @Autowired
    private BankService bankService;

    @Autowired
    private CardRepository cardRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AES aes;
    @Autowired
    private RestTemplate restTemplate;

    // todo: merchPass hash
    @PostMapping("/get-payment-url")
    public ResponseEntity<PaymentDataDTO> getPaymentUrl(@RequestBody PaymentRequestDTO request) {
        PaymentDataDTO paymentData = bankService.getPaymentUrl(request);
        return ResponseEntity.ok(paymentData);
    }

    @PostMapping("/pay-by-card")
    public void payByCard(@RequestBody CardAmountDTO cardAmountDTO) {
        Transaction transaction = bankService.checkBankForCard(cardAmountDTO);

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        // final step - send transaction information to the payment concentrator
        restTemplate.postForObject("http://localhost:8443/pc/finish-transaction", transactionDTO, TransactionDTO.class);
    }
}
