package com.sep.pcc.paymentcardcentre.controller;

import com.sep.pcc.paymentcardcentre.entity.Bank;
import com.sep.pcc.paymentcardcentre.entity.dto.AcquirerDataDTO;
import com.sep.pcc.paymentcardcentre.entity.dto.CardDTO;
import com.sep.pcc.paymentcardcentre.entity.dto.PaymentResultDTO;
import com.sep.pcc.paymentcardcentre.service.PccService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PccController {

    @Autowired
    private PccService pccService;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(PccController.class);

    private static final String PORT = "8762/";
    private static final String HOST = "http://localhost:";
    private static final String PATH = "/pay-by-card-forwarded";

    @PostMapping("/forward-to-bank")
    public PaymentResultDTO forwardToBank(@RequestBody AcquirerDataDTO acquirerDataDTO) {
        logger.info("Request - forward to the customer's bank. Acquirer order id: {}", acquirerDataDTO.getAcquirerOrderId());

        CardDTO cardDTO = acquirerDataDTO.getCard();

        Bank bank = pccService.findBank(cardDTO.getPan());

        String bankUrl = HOST + PORT + bank.getBankName() + PATH;

        logger.info("Forward to the: {}", bank.getBankName());

        // vraca banci prodavca acqOrderId acqTimestamp, issuerOrderId, issuerTimestap i status transakcije
        // todo: setovati header zbog autorizacije
        return restTemplate.postForObject(bankUrl, acquirerDataDTO, PaymentResultDTO.class);
    }
}
