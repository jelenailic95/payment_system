package com.sep.bank.bankservice.entity.dto;

import java.util.Date;

public class CardDTO {

    private String pan;
    private int securityCode;
    private String hardHolderName;
    private Date expirationDate;
    private double ammount;
    private int merchantOrderId;
    private String paymentId;

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

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
