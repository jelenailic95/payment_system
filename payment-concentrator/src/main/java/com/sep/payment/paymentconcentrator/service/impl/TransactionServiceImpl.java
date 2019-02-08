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

    /**
     * Method returns url based on the transaction status.
     *
     * @param transaction transaction that is being created
     * @return success, fail or error url
     */
    @Override
    public String finishTransaction(Transaction transaction) {
        transactionRepository.save(transaction);

        // set status on the payment request
        PaymentRequest paymentRequest = paymentRequestService.getPaymentRequest(transaction.getMerchantOrderId());
        paymentRequest.setStatus(transaction.getStatus());
        paymentRequestService.save(paymentRequest);

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
