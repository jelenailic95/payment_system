package com.sep.bank.bankservice.controller;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.entity.dto.RegisterNewAccountDTO;
import com.sep.bank.bankservice.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/create-account")
    public ResponseEntity<Account> registerNewAccount(@RequestBody RegisterNewAccountDTO request) {
        Account account = bankService.registerNewAccount(request.getFullName(), request.getEmail(), request.getBankName());
        return ResponseEntity.ok(account);
    }
}
