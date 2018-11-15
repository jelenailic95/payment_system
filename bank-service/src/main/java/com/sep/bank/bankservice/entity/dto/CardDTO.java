package com.sep.bank.bankservice.entity.dto;

import java.util.Date;

public class CardDTO {

    private String pan;
    private int securityCode;
    private String hardHolderName;
    private Date expirationDate;

    public CardDTO() {
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

    public String getHardHolderName() {
        return hardHolderName;
    }

    public void setHardHolderName(String hardHolderName) {
        this.hardHolderName = hardHolderName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
