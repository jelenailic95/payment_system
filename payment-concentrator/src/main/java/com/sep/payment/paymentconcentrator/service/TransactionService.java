package com.sep.payment.paymentconcentrator.service;

import com.sep.payment.paymentconcentrator.domain.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    String finishTransaction(Transaction transaction);
}
