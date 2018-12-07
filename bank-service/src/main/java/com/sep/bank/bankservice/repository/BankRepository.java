package com.sep.bank.bankservice.repository;

import com.sep.bank.bankservice.entity.Bank;
import com.sep.bank.bankservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByName(String name);
}
