package com.sep.payment.paymentconcentrator.repository;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentClientRepository extends JpaRepository<PaymentClient, Long> {
}
