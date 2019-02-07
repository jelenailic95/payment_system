package com.sep.payment.paymentconcentrator.controller;

import com.sep.payment.paymentconcentrator.domain.dto.FinishResponseDto;
import com.sep.payment.paymentconcentrator.domain.dto.TransactionResultUrlDTO;
import com.sep.payment.paymentconcentrator.domain.dto.TransactionResultDTO;
import com.sep.payment.paymentconcentrator.domain.entity.Transaction;
import com.sep.payment.paymentconcentrator.repository.PaymentRequestRepository;
import com.sep.payment.paymentconcentrator.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/pc")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping(value = "/finish-transaction")
    public ResponseEntity<TransactionResultUrlDTO> finishTransaction(@RequestBody TransactionResultDTO transactionDTO) {
        logger.info("Request - finish transaction. Transaction status: {}", transactionDTO.getStatus());

        Transaction transaction = new Transaction(transactionDTO.getMerchantOrderId(),
                transactionDTO.getAcquirerOrderId(), transactionDTO.getAcquirerTimestamp(),
                transactionDTO.getPaymentId(), transactionDTO.getStatus(), transactionDTO.getAmount());

        String resultUrl = transactionService.finishTransaction(transaction);

        logger.info("Result url is: {}", resultUrl);

        TransactionResultUrlDTO transactionCustomer = new TransactionResultUrlDTO(transaction.getMerchantOrderId(),
                transaction.getAcquirerOrderId(), transaction.getAcquirerTimestamp(),
                transaction.getPaymentId(), resultUrl, transaction.getAmount(), transaction.getStatus());

        PaymentRequest p = paymentRequestRepository.findByMerchantOrderId(transactionDTO.getMerchantOrderId());
        restTemplate.postForEntity("https://localhost:8000/".concat(p.getScName()).concat("/successful-payment"),
                p, String.class);
        return ResponseEntity.ok().body(transactionCustomer);
    }

    @PostMapping(value = "/successful-transaction/{id}")
    public String successfulTransaction(@PathVariable String id) {
        logger.info("Request - successful transaction.");
        PaymentRequest p = paymentRequestRepository.getOne(Long.parseLong(id));
//        FinishResponseDto finishPaymentDTO = FinishResponseDto.builder().typeOfPayment(p.getTypeOfPayment()).
//                journalName(p.getJournalName()).paperId(p.getPaperId()).username(p.getUsername()).scName(p.getScName()).build();

        restTemplate.postForEntity("https://localhost:8000/".concat(p.getScName()).concat("/successful-payment"),
                p, String.class);
        return "ok";
    }
}
