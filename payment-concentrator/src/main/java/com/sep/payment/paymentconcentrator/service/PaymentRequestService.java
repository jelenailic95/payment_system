package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentRequestService {
    PaymentRequest createPaymentRequest(String clientID, double amount, String bankName);
    PaymentRequest getPaymentRequest(Long merchantOrderId);
    PaymentRequest save(PaymentRequest paymentRequest);
}
