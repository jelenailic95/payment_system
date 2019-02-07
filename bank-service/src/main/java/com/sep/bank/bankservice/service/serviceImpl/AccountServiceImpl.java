package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.repository.AccountRepository;
import com.sep.bank.bankservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account checkMerchantData(String merchantId, String merchantPassword) {
        return accountRepository.findByMerchantIdAndMerchantPassword(merchantId, merchantPassword);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long id) {
        return accountRepository.getOne(id);
    }

}
