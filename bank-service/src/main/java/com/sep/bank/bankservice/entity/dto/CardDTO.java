package com.sep.bank.bankservice.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sep.bank.bankservice.entity.Account;

import java.util.Date;

public class CardDTO {

    private String pan;
    private int securityCode;
    private String cardHolderName;
    private Date expirationDate;

    public CardDTO() {
    }

    public CardDTO(String pan, int securityCode, String cardHolderName, Date expirationDate) {
        this.pan = pan;
        this.securityCode = securityCode;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
    }

    public String getPan() {

        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardColderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
