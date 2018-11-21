package com.sep.pcc.paymentcardcentre.controller;

import com.sep.pcc.paymentcardcentre.service.PccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private PccService pccService;

    @PostMapping("/forward-to-bank")
    public ResponseEntity<?> forwardToBank(@RequestBody String  request) {

        return ResponseEntity.ok("");
    }
}
