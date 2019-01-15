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
        // if data is encrypted decrypt it
        if (encrypted) {
            pan = aes.decrypt(pan);
            code = aes.decrypt(code);
            name = aes.decrypt(name);
            date = aes.decrypt(date);
        }

        // get all credit cards
        List<Card> cards = getAll();

        for (Card c : cards) {
            // check if the PAN number is the same
            if (aes.decrypt(c.getPan()).equals(pan)) {

                // if the PAN number is the same, check if other fields are equal with the given card data
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
