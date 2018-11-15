package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.Account;
import com.sep.bank.bankservice.entity.Bank;
import com.sep.bank.bankservice.entity.Card;
import com.sep.bank.bankservice.entity.User;
import com.sep.bank.bankservice.entity.dto.*;
import com.sep.bank.bankservice.repository.BankRepository;
import com.sep.bank.bankservice.service.AccountService;
import com.sep.bank.bankservice.service.BankService;
import com.sep.bank.bankservice.service.CardService;
import com.sep.bank.bankservice.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.bouncycastle.util.test.TestRandomData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
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

    @Autowired
    private CardService cardService;

    @Autowired
    private RestTemplate restTemplate;

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
                    RandomStringUtils.randomAlphabetic(16),
                    requestDTO.getAmount(),
                    requestDTO.getMerchantOrderId());
        }
        return paymentDataDTO;
    }

    @Override
    public String checkBankForCard(CardDTO card) {
        Card foundCard = cardService.findCard(card.getPan(),card.getSecurityCode(), card.getHardHolderName(), card.getExpirationDate());
        TransactionDTO transactionDTO = new TransactionDTO(card.getMerchantOrderId(), card.getPaymentId(),0,null);

        if(foundCard != null){
            if(checkAmmountOnAccount(foundCard, card.getAmmount())){
                transactionDTO.setStatus("SUCCESS");
            }else
                transactionDTO.setStatus("FAILED");
            restTemplate.postForEntity("http://localhost:8443/pc/finish-transaction", transactionDTO, String.class);
        }else{
            forwardToPcc(foundCard, transactionDTO);
        }
        return null;
    }

    private void forwardToPcc(Card foundCard, TransactionDTO transactionDTO) {
        Random random = new Random();
        AcquirerDataDTO acquirerDataDTO = new AcquirerDataDTO(random.nextInt(), new Date(), foundCard);
        restTemplate.postForEntity("http://localhost:8444/forward-to-bank", acquirerDataDTO, String.class);

    }

    private boolean checkAmmountOnAccount(Card foundCard, double ammount) {
        if(ammount < foundCard.getAccount().getAmmount())
            return true;
        return false;
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
