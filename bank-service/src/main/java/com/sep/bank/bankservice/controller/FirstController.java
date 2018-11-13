package com.sep.bank.bankservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class FirstController {


    @RequestMapping("/getAll")
    public String getAll() {
        List<String> images = Arrays.asList(
                "Jelena", "Marija", "David");
        return "Jelena, Marija, David";
    }


}
