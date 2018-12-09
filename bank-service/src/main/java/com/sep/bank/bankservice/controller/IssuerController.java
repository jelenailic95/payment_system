package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.TransactionStatus;
import com.sep.bank.bankservice.entity.dto.AcquirerDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentResultDTO;
import org.modelmapper.ModelMapper;
import com.sep.bank.bankservice.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Transaction transaction = bankService.checkCard(acquirerDataDTO);
        Random random = new Random();
        return new PaymentResultDTO(acquirerDataDTO.getAcquirerOrderId(),
                acquirerDataDTO.getAcquirerTimestamp(), random.nextLong(), new Date(), transaction.getStatus());
    }
}


