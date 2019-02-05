package com.sep.bank.bankservice.repository;

import com.sep.bank.bankservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    PaymentRequest findByPaymentUrl(String paymentUrl);
}
