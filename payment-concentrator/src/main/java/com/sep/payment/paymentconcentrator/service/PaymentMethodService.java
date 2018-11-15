package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PaymentMethodService {

    Set<PaymentMethod> getPaymentMethods(Long clientId);

    PaymentRequest createPaymentRequest(Long clientID, double amount);
}
