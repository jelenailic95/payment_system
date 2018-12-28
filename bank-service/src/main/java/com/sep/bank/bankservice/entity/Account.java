package com.sep.bank.bankservice.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String accountNumber;

    @Column
    private double amount;

    @OneToOne
    private User cardHolder;

    @Column
    @Size(max = 100)
    private String merchantId;

    @Column
    @Size(max = 30)
    private String merchantPassword;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(User cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }
}
