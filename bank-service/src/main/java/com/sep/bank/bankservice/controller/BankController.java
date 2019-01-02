package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.entity.dto.RegisterNewAccountDTO;
import com.sep.bank.bankservice.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    private Logger logger = LoggerFactory.getLogger(BankController.class);

    @PostMapping("/create-account")
    public ResponseEntity<Account> registerNewAccount(@RequestBody RegisterNewAccountDTO request) {
        logger.info("Request - register new account for the bank card payment system. New client: {}",
                request.getFullName());
        Account account = bankService.registerNewAccount(request.getFullName(), request.getEmail(), request.getBankName());

        logger.info("New account is successfully registered.");
        return ResponseEntity.ok(account);
    }

//    @GetMapping(value = "/get-token")
//    public ResponseEntity<String> getToken() throws UnsupportedEncodingException {
//        Algorithm algorithm = Algorithm.HMAC256("s4T2zOIWHMM1sxq");
//        String token = JWT.create()
//                .withClaim("client", "Laguna")
//                .sign(algorithm);
//        return ResponseEntity.ok(token);
//    }
}
