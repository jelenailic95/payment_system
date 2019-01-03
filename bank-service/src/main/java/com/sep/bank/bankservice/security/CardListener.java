package com.sep.bank.bankservice.security;

import com.sep.bank.bankservice.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;

public class CardListener {
    @Autowired
    private AES aes;

    @PrePersist
    public void prePersist(Card card) {
        card.setCardHolderName(aes.encrypt(card.getCardHolderName()));
        card.setSecurityCode(aes.encrypt(card.getSecurityCode()));
        card.setPan(aes.encrypt(card.getPan()));
        card.setExpirationDate(aes.encrypt(card.getExpirationDate()));
    }
}
