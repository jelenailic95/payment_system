package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.*;
import com.sep.bank.bankservice.entity.dto.*;
import com.sep.bank.bankservice.repository.BankRepository;
import com.sep.bank.bankservice.repository.TransactionRepository;
import com.sep.bank.bankservice.service.AccountService;
import com.sep.bank.bankservice.service.BankService;
import com.sep.bank.bankservice.service.CardService;
import com.sep.bank.bankservice.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    @Autowired
    private TransactionRepository transactionRepository;

    private ModelMapper modelMapper;

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public PaymentDataDTO getPaymentUrl(PaymentRequestDTO requestDTO) {

        Account account = accountService.checkMerchantData(requestDTO.getMerchantId(), requestDTO.getMerchantPassword());
        PaymentDataDTO paymentDataDTO = new PaymentDataDTO();
        if (account != null) {
            paymentDataDTO = new PaymentDataDTO(
                    RandomStringUtils.randomAlphabetic(16),
                    RandomStringUtils.randomAlphabetic(16),
                    requestDTO.getAmount(),
                    requestDTO.getMerchantOrderId());
        }
        return paymentDataDTO;
    }

    @Override
    public Transaction checkCard(AcquirerDataDTO acquirerDataDTO) {
        CardDTO card = acquirerDataDTO.getCard();
        Card foundCard = cardService.findCard(card.getPan(), card.getSecurityCode(), card.getCardHolderName(), card.getExpirationDate());

        if (foundCard != null) {
            if (checkAmountOnAccount(foundCard, acquirerDataDTO.getAmount())) {
                return createTransaction(acquirerDataDTO, TransactionStatus.PAID);
            } else
                return createTransaction(acquirerDataDTO, TransactionStatus.REFUSED);
        }
        return createTransaction(acquirerDataDTO, TransactionStatus.FAILED);  // ne pronalazi karticu u banci, neki error baciti
    }

    private Transaction createTransaction(AcquirerDataDTO acquirerDataDTO, TransactionStatus status) {
        Transaction transaction = new Transaction();
        transaction.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        transaction.setAmount(acquirerDataDTO.getAmount());
        transaction.setStatus(status);
        transaction.setMerchantOrderId(acquirerDataDTO.getMerchantOrderId());
        transaction.setPaymentId(acquirerDataDTO.getPaymentId());
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction checkBankForCard(CardAmountDTO card) {
        Card foundCard = cardService.findCard(card.getPan(), card.getSecurityCode(), card.getCardHolderName(),
                card.getExpirationDate());
        Transaction transaction = new Transaction();
        transaction.setMerchantOrderId(card.getMerchantOrderId());
        transaction.setPaymentId(card.getPaymentId());
        transaction.setAmount(card.getAmount());

        if (foundCard != null) {
            if (checkAmountOnAccount(foundCard, card.getAmount())) {
                transaction.setStatus(TransactionStatus.PAID);
            } else
                transaction.setStatus(TransactionStatus.REFUSED);
        } else {
            // banks are different
            transaction = forwardToPcc(card, transaction);
            return transaction;
        }
        transactionRepository.save(transaction);
        return transaction;
    }

    private Transaction forwardToPcc(CardAmountDTO cardAmountDTO, Transaction transaction) {
        Random random = new Random();
        CardDTO cardDTO = new CardDTO(cardAmountDTO.getPan(), cardAmountDTO.getSecurityCode(),
                cardAmountDTO.getCardHolderName(), cardAmountDTO.getExpirationDate());

        AcquirerDataDTO acquirerDataDTO = new AcquirerDataDTO(random.nextLong(), new Date(), cardDTO,
                transaction.getAmount(), transaction.getMerchantOrderId(), transaction.getPaymentId());

        transaction.setAcquirerTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        transaction.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());

        // poziva PCC koji prosledjuje podatke banci kupca i vraca status
        PaymentResultDTO paymentResultDTO = restTemplate.postForObject("http://localhost:8444/forward-to-bank",
                acquirerDataDTO, PaymentResultDTO.class);

        if (paymentResultDTO != null) {
            transaction.setStatus(paymentResultDTO.getStatus());
        }
        return transaction;
    }

    private boolean checkAmountOnAccount(Card foundCard, double amount) {
        if (amount <= foundCard.getAccount().getAmount()) {
            foundCard.getAccount().setAmount(foundCard.getAccount().getAmount() - amount);
            accountService.saveAccount(foundCard.getAccount());
            return true;
        }
        return false;
    }

    @Override
    public Account registerNewAccount(String name, String email, String bankName) {
        Bank bank = bankRepository.findByName(bankName);
        User user = userService.create(new User(name, email));

        Account newAccount = new Account();
        newAccount.setCardHolder(user);
        newAccount.setMerchantId(RandomStringUtils.randomAlphabetic(64));
        newAccount.setMerchantPassword(RandomStringUtils.randomAlphabetic(16));
        newAccount.setAccountNumber(RandomStringUtils.randomAlphabetic(16));
        Account createdAccount = accountService.create(newAccount);

        if (bank.getAccounts() == null) {
            HashSet accounts = new HashSet();
            accounts.add(createdAccount);
            bank.setAccounts(accounts);
        } else {
            bank.getAccounts().add(createdAccount);
        }
        bankRepository.save(bank);

        return createdAccount;
    }


}
