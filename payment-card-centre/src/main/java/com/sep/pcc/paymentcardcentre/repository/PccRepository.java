package com.sep.pcc.paymentcardcentre.repository;

import com.sep.pcc.paymentcardcentre.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PccRepository extends JpaRepository<Bank, Long> {
    Bank findByBankIdentifier(int bankIdentifier);
}
