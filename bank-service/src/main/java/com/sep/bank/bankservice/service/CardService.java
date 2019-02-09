package com.sep.bank.bankservice.service;

import com.sep.bank.bankservice.entity.Card;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardService {

    List<Card> getAll();
    Card findCard(String pan, String code, String name, String date, Boolean encrypted);
}
