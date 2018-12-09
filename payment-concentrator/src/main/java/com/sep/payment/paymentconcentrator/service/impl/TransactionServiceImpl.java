package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.TransactionStatus;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.domain.entity.Transaction;
import com.sep.payment.paymentconcentrator.repository.TransactionRepository;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import com.sep.payment.paymentconcentrator.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Override
    public String finishTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        PaymentRequest paymentRequest = paymentRequestService.getPaymentRequest(transaction.getMerchantOrderId());
        TransactionStatus status = transaction.getStatus();
        String url;
        switch (status) {
            case PAID:
                url = paymentRequest.getSuccessUrl();
                break;
            case FAILED:
                url = paymentRequest.getFaildUrl();
                break;
            default:
                url = paymentRequest.getErrorUrl();
                break;
        }
        return url;
    }

}
