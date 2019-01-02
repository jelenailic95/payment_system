package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.TransactionResultCustomerDTO;
import com.sep.payment.paymentconcentrator.domain.dto.TransactionResultDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Transaction;
import com.sep.payment.paymentconcentrator.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pc")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private ModelMapper modelMapper = new ModelMapper();

    private Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping(value = "/finish-transaction")
    public ResponseEntity<TransactionResultCustomerDTO> finishTransaction(@RequestBody TransactionResultDTO transactionDTO) {
        logger.info("Request - finish transaction. Transaction status: {}", transactionDTO.getStatus());
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        String resultUrl = transactionService.finishTransaction(transaction);

        logger.info("Result url is: {}", resultUrl);

        TransactionResultCustomerDTO transactionCustomer = new TransactionResultCustomerDTO(transaction.getMerchantOrderId(),
                transaction.getAcquirerOrderId(), transaction.getAcquirerTimestamp(),
                transaction.getPaymentId(), resultUrl, transaction.getAmount(), transaction.getStatus());

        System.out.println(transactionCustomer.getResultUrl());
        System.out.println(transactionCustomer.getAcquirerOrderId());
        System.out.println(transactionCustomer.getAmount());

        return ResponseEntity.ok().body(transactionCustomer);
    }
}
