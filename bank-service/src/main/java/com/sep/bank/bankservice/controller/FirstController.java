package com.sep.bank.bankservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class FirstController {

    @RequestMapping("/getAll")
    public List<String> getAll() {
        List<String> images = Arrays.asList(
                "Jelena", "Marija", "David");
        return images;
    }
}
