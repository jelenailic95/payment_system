package com.sep.bank.bankservice.repository;

import com.sep.bank.bankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByMerchantIdAndMerchantPassword(String id, String password);
}
