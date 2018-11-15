package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentRequestService {
    PaymentRequest createPaymentRequest(Long clientID, double amount);
    PaymentRequest getPaymentRequest(int merchantOrderId);
}
