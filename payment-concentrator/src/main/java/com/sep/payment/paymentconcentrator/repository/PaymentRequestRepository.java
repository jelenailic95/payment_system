package com.sep.payment.paymentconcentrator.repository;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    PaymentRequest findByMerchantOrderId(Long merchantOrderId);
}
