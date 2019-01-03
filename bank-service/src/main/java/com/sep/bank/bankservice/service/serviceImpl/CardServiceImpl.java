package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.Card;
import com.sep.bank.bankservice.repository.CardRepository;
import com.sep.bank.bankservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card findCard(String pan, String securityCode, String cardHolder, String expiration) {
        return cardRepository.findByPanAndSecurityCodeAndCardHolderName(pan,securityCode,cardHolder);
    }
}
