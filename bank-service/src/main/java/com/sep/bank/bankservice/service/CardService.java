package com.sep.bank.bankservice.service;

import com.sep.bank.bankservice.entity.Card;
import org.springframework.stereotype.Service;

@Service
public interface CardService {

    Card findCard(String pan, String securityCode, String cardHolder, String expiration);
}
