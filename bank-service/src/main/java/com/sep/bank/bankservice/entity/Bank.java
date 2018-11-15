package com.sep.bank.bankservice.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Bank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String address;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @Size(min=10, max = 10)
    private String accountNumber;

    @Column(unique = true)
    @Size(min=3, max =3)
    private String uniqueBankNumber;

    @Column
    private String phoneNumber;

    @OneToMany
    private Set<Account> accounts;

    public Bank() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public String getUniqueBankNumber() {
        return uniqueBankNumber;
    }

    public void setUniqueBankNumber(String uniqueBankNumber) {
        this.uniqueBankNumber = uniqueBankNumber;
    }
}

