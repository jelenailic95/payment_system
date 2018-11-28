package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.dto.CardAmountDTO;
import com.sep.bank.bankservice.entity.dto.PaymentDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentRequestDTO;
import com.sep.bank.bankservice.entity.dto.TransactionDTO;
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

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/get-payment-url")
    public ResponseEntity<PaymentDataDTO> getPaymentUrl(@RequestBody PaymentRequestDTO request) {
        PaymentDataDTO paymentData = bankService.getPaymentUrl(request);
        return ResponseEntity.ok(paymentData);
    }

    @PostMapping("/pay-by-card")
    public ResponseEntity<?> payByCard(@RequestBody CardAmountDTO cardAmountDTO) {
        Transaction transaction = bankService.checkBankForCard(cardAmountDTO);

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        // final step - send transaction information to the payment concentrator
        restTemplate.postForEntity("http://localhost:8443/pc/finish-transaction", transactionDTO, String.class);

        return ResponseEntity.ok("");
    }
}
