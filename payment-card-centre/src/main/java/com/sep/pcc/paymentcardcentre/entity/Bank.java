package com.sep.pcc.paymentcardcentre.entity;

import javax.persistence.*;

@Entity
@Table
public class Bank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int bankIdNumber;
    @Column
    private String name;

    public Bank() {
    }

    public int getBankIdNumber() {
        return bankIdNumber;
    }

    public void setBankIdNumber(int bankIdNumber) {
        this.bankIdNumber = bankIdNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
