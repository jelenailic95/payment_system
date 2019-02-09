package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.dto.AcquirerDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentResultDTO;
import com.sep.bank.bankservice.service.BankService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class IssuerController {

    private BankService bankService;

    private ModelMapper modelMapper = new ModelMapper();

    private Logger logger = LoggerFactory.getLogger(IssuerController.class);

    @Autowired
    public IssuerController(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Request came from the PCC. Card is forwarded to this, second bank. Checking if card exists and trying to
     * process transaction.
     *
     * @param acquirerDataDTO card information, acquirer order id, acquirer timestamp and amount
     * @return acquirer order id, issuer order id, issuer timestamp, payment status
     */
    @PostMapping("/pay-by-card-forwarded")
    public PaymentResultDTO payByCardForwarded(@RequestBody AcquirerDataDTO acquirerDataDTO) {
        logger.info("Request - forwarded payment details from the seller's bank. Acquirer order: {}",
                acquirerDataDTO.getAcquirerOrderId());

        Transaction transaction = bankService.checkCard(acquirerDataDTO);

        logger.info("Transaction is done. Transaction status: {}", transaction.getStatus());
        logger.info("Transaction result is returned to the seller's bank.");

        Long issuerOrderId = bankService.getIssuerOrderId();
        return new PaymentResultDTO(acquirerDataDTO.getAcquirerOrderId(),
                acquirerDataDTO.getAcquirerTimestamp(), issuerOrderId, new Date(), transaction.getStatus());
    }
}


