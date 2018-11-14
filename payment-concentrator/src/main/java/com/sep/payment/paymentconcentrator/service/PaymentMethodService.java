package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PaymentMethodService {

    Set<PaymentMethod> getPaymentMethods(Long clientId);
}
