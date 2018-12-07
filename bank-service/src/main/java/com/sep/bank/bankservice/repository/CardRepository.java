package com.sep.bank.bankservice.repository;

import com.sep.bank.bankservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByPanAndSecurityCodeAndCardHolderNameAndExpirationDate(String pan, int securityCode, String cardHolder, Date expiration);
    Card findByPanAndSecurityCodeAndCardHolderName(String pan, int securityCode, String cardHolder);


}
