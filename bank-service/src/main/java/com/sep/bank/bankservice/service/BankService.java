package com.sep.bank.bankservice.service;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.entity.Bank;
import com.sep.bank.bankservice.entity.PaymentRequest;
import com.sep.bank.bankservice.entity.Transaction;
import com.sep.bank.bankservice.entity.dto.AcquirerDataDTO;
import com.sep.bank.bankservice.entity.dto.CardAmountDTO;
import com.sep.bank.bankservice.entity.dto.PaymentDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankService {

    Account registerNewAccount(String name, String email, String bankName);
    List<Bank> getAll();
    PaymentDataDTO getPaymentUrl(PaymentRequestDTO requestDTO);
    Transaction checkBankForCard(CardAmountDTO card);
    Transaction checkCard(AcquirerDataDTO acquirerDataDTO);
    Long getIssuerOrderId();
    PaymentRequest getPaymentRequest(String paymentUrl);
}
