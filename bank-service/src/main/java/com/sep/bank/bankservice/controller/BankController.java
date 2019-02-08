package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.entity.PaymentRequest;
import com.sep.bank.bankservice.entity.dto.PaymentDataDTO;
import com.sep.bank.bankservice.entity.dto.RegisterNewAccountDTO;
import com.sep.bank.bankservice.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BankController {

    private BankService bankService;

    private Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * POST: create new account in the bank.
     *
     * @param request user data
     * @return created account
     */
    @PostMapping("/create-account")
    public ResponseEntity<Account> registerNewAccount(@RequestBody RegisterNewAccountDTO request) {
        logger.info("Request - register new account for the bank card payment system. New client: {}",
                request.getFullName());
        Account account = bankService.registerNewAccount(request.getFullName(), request.getEmail(), request.getBankName());

        logger.info("New account is successfully registered.");
        return ok(account);
    }

    /**
     * Get payment request for the given payment url.
     *
     * @param paymentUrl payment url
     * @return payment request from the db
     */
    @PostMapping("/payment")
    private ResponseEntity getPaymentRequest(@RequestBody String paymentUrl) {
        logger.info("Request - get payment request for the payment url: {}", paymentUrl);
        PaymentRequest paymentRequest = bankService.getPaymentRequest(paymentUrl);
        PaymentDataDTO paymentDataDTO = new PaymentDataDTO();

        if (paymentRequest != null) {
            paymentDataDTO.setAmount(paymentRequest.getAmount());
            paymentDataDTO.setMerchantOrderId(paymentRequest.getMerchantOrderId());
            paymentDataDTO.setPaymentId(paymentRequest.getPaymentId());
        }

        logger.info("Returned payment request: {}", paymentDataDTO.toString());

        return ResponseEntity.ok(paymentDataDTO);
    }
}
