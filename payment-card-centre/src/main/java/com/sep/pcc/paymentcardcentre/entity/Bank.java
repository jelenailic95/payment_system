package com.sep.pcc.paymentcardcentre.entity;

import javax.persistence.*;

@Entity
@Table
public class Bank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int bankIdentifier;

    @Column
    private String serviceName;

    @Column
    private String bankName;

    public Bank() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBankIdentifier() {
        return bankIdentifier;
    }

    public void setBankIdentifier(int bankIdentifier) {
        this.bankIdentifier = bankIdentifier;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
