package com.sep.bank.bankservice.service;

import com.sep.bank.bankservice.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    Account create(Account account);
    Account checkMerchantData(String merchantId, String merchantPassword);
    void saveAccount(Account account);

}
