package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.entity.Bank;
import com.sep.bank.bankservice.entity.User;
import com.sep.bank.bankservice.entity.dto.CardDTO;
import com.sep.bank.bankservice.entity.dto.PaymentDataDTO;
import com.sep.bank.bankservice.entity.dto.PaymentRequestDTO;
import com.sep.bank.bankservice.repository.AccountRepository;
import com.sep.bank.bankservice.repository.BankRepository;
import com.sep.bank.bankservice.service.AccountService;
import com.sep.bank.bankservice.service.BankService;
import com.sep.bank.bankservice.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public PaymentDataDTO getPaymentUrl(PaymentRequestDTO requestDTO) {
        Account account = accountService.checkMerchantData(requestDTO.getMerchantId(), requestDTO.getMerchantPassword());
        PaymentDataDTO paymentDataDTO = new PaymentDataDTO();
        if(account != null){
            paymentDataDTO = new PaymentDataDTO(
                    RandomStringUtils.randomAlphabetic(16),
                    RandomStringUtils.randomAlphabetic(16));
        }
        return paymentDataDTO;
    }

    @Override
    public String checkBankForCard(CardDTO card) {
        return null;
    }

    @Override
    public Account registerNewAccount(String name, String email, String bankName){
        Bank bank = bankRepository.findByName(bankName);
        User user = userService.create(new User(name, email));

        Account newAccount = new Account();
        newAccount.setCardHolder(user);
        newAccount.setMerchantId(RandomStringUtils.randomAlphabetic(64));
        newAccount.setMerchantPassword(RandomStringUtils.randomAlphabetic(16));
        newAccount.setAccountNumber(RandomStringUtils.randomAlphabetic(16));
        Account createdAccount = accountService.create(newAccount);

        bank.getAccounts().add(createdAccount);
        bankRepository.save(bank);

        return createdAccount;
    }


}
