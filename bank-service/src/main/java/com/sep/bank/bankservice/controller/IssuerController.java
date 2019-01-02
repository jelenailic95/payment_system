package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.TransactionStatus;
import com.sep.bank.bankservice.entity.dto.AcquirerDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentResultDTO;
import org.modelmapper.ModelMapper;
import com.sep.bank.bankservice.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(IssuerController.class);

    @PostMapping("/pay-by-card-forwarded")
    public PaymentResultDTO payByCardForwarded(@RequestBody AcquirerDataDTO acquirerDataDTO) {
        logger.info("Request - forwarded payment details from the seller's bank. Acquirer order: {}",
                acquirerDataDTO.getAcquirerOrderId());

        Transaction transaction = bankService.checkCard(acquirerDataDTO);

        logger.info("Transaction is done. Transaction status: {}", transaction.getStatus());
        logger.info("Transaction result is returned to the seller's bank.");

        Random random = new Random();
        return new PaymentResultDTO(acquirerDataDTO.getAcquirerOrderId(),
                acquirerDataDTO.getAcquirerTimestamp(), random.nextLong(), new Date(), transaction.getStatus());
    }
}


