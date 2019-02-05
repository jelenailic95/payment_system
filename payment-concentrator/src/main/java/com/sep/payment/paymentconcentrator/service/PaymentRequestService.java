package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentRequestService {
    PaymentRequest createPaymentRequest(String clientID, double amount, String bankName);
    PaymentRequest createRequest(String username, double amount, String journalName, Long paperId, String typeOfPayment);
    PaymentRequest getPaymentRequest(Long merchantOrderId);
    PaymentRequest save(PaymentRequest paymentRequest);
}
