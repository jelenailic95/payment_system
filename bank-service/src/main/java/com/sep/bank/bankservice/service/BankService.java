package com.sep.bank.bankservice.service;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.entity.Bank;
import com.sep.bank.bankservice.entity.dto.CardDTO;
import com.sep.bank.bankservice.entity.dto.PaymentDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankService {

    Account registerNewAccount(String name, String email, String bankName);
    List<Bank> getAll();
    PaymentDataDTO getPaymentUrl(PaymentRequestDTO requestDTO);
    String checkBankForCard(CardDTO card);
}
