package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.Card;
import com.sep.bank.bankservice.repository.CardRepository;
import com.sep.bank.bankservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card findCard(String pan, int securityCode, String cardHolder, Date expiration) {
        return cardRepository.findByPanAndSecurityCodeAndCardHolderNameAndExpirationDate(pan,securityCode,cardHolder,expiration);
    }
}
