package com.sep.pcc.paymentcardcentre.repository;

import com.sep.pcc.paymentcardcentre.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Bank, Long> {


}
