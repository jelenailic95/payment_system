package com.sep.bank.bankservice.repository;

import com.sep.bank.bankservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PostLoad;
import java.util.Date;
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByPanAndSecurityCodeAndCardHolderNameAndExpirationDate(String pan, String securityCode, String cardHolder, String expiration);
    Card findByPanAndSecurityCodeAndCardHolderName(String pan, String securityCode, String cardHolder);


}
