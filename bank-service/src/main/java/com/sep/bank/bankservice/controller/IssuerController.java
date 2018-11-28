package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.dto.AcquirerDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentResultDTO;
import org.modelmapper.ModelMapper;
import com.sep.bank.bankservice.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class IssuerController {
    @Autowired
    private BankService bankService;
    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/pay-by-card-forwarded")
    public PaymentResultDTO payByCardForwarded(@RequestBody AcquirerDataDTO acquirerDataDTO) {
        String status = bankService.checkCard(acquirerDataDTO);

        PaymentResultDTO paymentResultDTO = new PaymentResultDTO();
        paymentResultDTO.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());
        paymentResultDTO.setAcquirerTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        Random random = new Random();
        paymentResultDTO.setIssuerOrderId(random.nextLong());
        paymentResultDTO.setIssuerTimestamp(new Date());
        paymentResultDTO.setStatus(status);
        return paymentResultDTO;
    }
}
