package com.sep.payment.paymentconcentrator.repository;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    PaymentMethod findByMethodNameAndMethod(String methodName, String method);
    List<PaymentMethod> findByMethod(String method);

}
