package com.sep.paypal.repository;

import com.sep.paypal.model.entity.TransactionPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionPayment, Long> {
}
