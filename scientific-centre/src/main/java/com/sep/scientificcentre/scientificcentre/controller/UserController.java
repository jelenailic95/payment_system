package com.sep.scientificcentre.scientificcentre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "example")
    private ResponseEntity<?> example(){
        String s = "";
        s = restTemplate.getForObject("http://localhost:8443/pc/get-token", String.class);
        return ResponseEntity.ok(s);

    }
}
