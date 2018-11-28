package com.sep.payment.paymentconcentrator.repository;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    PaymentMethod findByName(String name);
}
