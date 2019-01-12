package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.Card;
import com.sep.bank.bankservice.repository.CardRepository;
import com.sep.bank.bankservice.security.AES;
import com.sep.bank.bankservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AES aes;

    @Override
    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findCard(String pan, String code, String name, String date, Boolean encrypted) {
        // check if data is encrypted
        if (encrypted) {
            pan = aes.decrypt(pan);
            code = aes.decrypt(code);
            name = aes.decrypt(name);
            date = aes.decrypt(date);
        }

        List<Card> cards = getAll();
        for (Card c : cards) {
            if (aes.decrypt(c.getPan()).equals(pan)) {
                if (aes.decrypt(c.getExpirationDate()).equals(date)
                        && aes.decrypt(c.getCardHolderName()).equals(name)
                        && aes.decrypt(c.getSecurityCode()).equals(code)) {
                    return c;
                }
            }
        }
        return null;
    }
}
