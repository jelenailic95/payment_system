package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.*;
import com.sep.bank.bankservice.entity.dto.*;
import com.sep.bank.bankservice.repository.BankRepository;
import com.sep.bank.bankservice.repository.TransactionRepository;
import com.sep.bank.bankservice.security.AES;
import com.sep.bank.bankservice.service.AccountService;
import com.sep.bank.bankservice.service.BankService;
import com.sep.bank.bankservice.service.CardService;
import com.sep.bank.bankservice.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
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

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AES aes;

    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public PaymentDataDTO getPaymentUrl(PaymentRequestDTO requestDTO) {
        Account account = accountService.checkMerchantData(requestDTO.getMerchantId(), requestDTO.getMerchantPassword());
        PaymentDataDTO paymentDataDTO = new PaymentDataDTO();
        if (account != null) {
            logger.info("This client exists in the system. Merchant id: {}", account.getMerchantId());
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
        Card foundCard = cardService.findCard(aes.encrypt(card.getPan()),
                aes.encrypt(card.getSecurityCode()),
                aes.encrypt(card.getCardHolderName()), aes.encrypt(card.getExpirationDate()));

        if (foundCard != null) {
            logger.info("This credit card exists in the bank system. Card holder name: {}", aes.decrypt(foundCard.getCardHolderName()));
            if (checkAmountOnAccount(foundCard, acquirerDataDTO.getAmount())) {
                logger.info("This client has enough money on the card. Transaction status: PAID.");
                return createTransaction(acquirerDataDTO, TransactionStatus.PAID);
            } else {
                logger.info("This client doesn't have enough money on the card. Status: REFUSED.");
                return createTransaction(acquirerDataDTO, TransactionStatus.REFUSED);
            }
        }
        logger.info("This credit card doesn't exist in the bank system. Transaction status: FAILED");
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
        logger.info("New transaction is successfully created and saved.");
        return transaction;
    }

    @Override
    public Transaction checkBankForCard(CardAmountDTO card) {
        Card foundCard = cardService.findCard(aes.encrypt(card.getPan()),
                aes.encrypt(card.getSecurityCode()),
                aes.encrypt(card.getCardHolderName()), aes.encrypt(card.getExpirationDate()));

        Transaction transaction = new Transaction();
        transaction.setMerchantOrderId(card.getMerchantOrderId());
        transaction.setPaymentId(card.getPaymentId());
        transaction.setAmount(card.getAmount());

        if (foundCard != null) {
            logger.info("This credit card exists in the bank system. Card holder name: {}", aes.decrypt(foundCard.getCardHolderName()));
            if (checkAmountOnAccount(foundCard, card.getAmount())) {
                logger.info("This client has enough money on the card. Transaction status: PAID.");
                transaction.setStatus(TransactionStatus.PAID);
            } else {
                logger.info("This client doesn't have enough money on the card. Status: REFUSED.");
                transaction.setStatus(TransactionStatus.REFUSED);
            }
        } else {
            // banks are different
            logger.info("This credit card doesn't exist in the bank system. Transaction request is forwarded to the PCC.");
            transaction = forwardToPcc(card, transaction);
            return transaction;
        }
        Transaction transactionSaved = transactionRepository.save(transaction);
        logger.info("Transaction is successfully saved. Transaction: {}", transactionSaved.getId());
        return transaction;
    }

    private Transaction forwardToPcc(CardAmountDTO cardAmountDTO, Transaction transaction) {
        Random random = new Random();

        CardDTO cardDTO = new CardDTO(aes.encrypt(cardAmountDTO.getPan()),
                aes.encrypt(cardAmountDTO.getSecurityCode()),
                aes.encrypt(cardAmountDTO.getCardHolderName()), aes.encrypt(cardAmountDTO.getExpirationDate()));

        AcquirerDataDTO acquirerDataDTO = new AcquirerDataDTO(random.nextLong(), new Date(), cardDTO,
                transaction.getAmount(), transaction.getMerchantOrderId(), transaction.getPaymentId());

        transaction.setAcquirerTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        transaction.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());
        logger.info("Transaction acquirer timestamp: {}", transaction.getAcquirerTimestamp());

        // poziva PCC koji prosledjuje podatke banci kupca i vraca status
        PaymentResultDTO paymentResultDTO = restTemplate.postForObject("http://localhost:8444/forward-to-bank",
                acquirerDataDTO, PaymentResultDTO.class);

        if (paymentResultDTO != null) {
            logger.info("Transaction result is returned from the client's bank. Transaction status: {}",
                    transaction.getStatus());
            transaction.setStatus(paymentResultDTO.getStatus());
        }
        return transaction;
    }

    private boolean checkAmountOnAccount(Card foundCard, double amount) {
        if (amount <= foundCard.getAccount().getAmount()) {
            logger.info("There is enough money on the credit card.");
            foundCard.getAccount().setAmount(foundCard.getAccount().getAmount() - amount);
            accountService.saveAccount(foundCard.getAccount());
            return true;
        }
        logger.info("There is no enough money on the credit card.");
        return false;
    }

    @Override
    public Account registerNewAccount(String name, String email, String bankName) {
        Bank bank = bankRepository.findByName(bankName);
        User user = userService.create(new User(name, email));

        Account newAccount = new Account();
        newAccount.setCardHolder(user);
        newAccount.setMerchantId(RandomStringUtils.randomAlphabetic(64));
        newAccount.setMerchantPassword(generateNewMerchantPassword(name, newAccount.getMerchantId()));
        newAccount.setAccountNumber(RandomStringUtils.randomAlphabetic(16));

        Account createdAccount = accountService.create(newAccount);
        logger.info("New registered account is successfully created and saved.");

        if (bank.getAccounts() == null) {
            logger.info("This bank doesn't have bank accounts. New registered account is the first one being added.");
            HashSet<Account> accounts = new HashSet<>();
            accounts.add(createdAccount);
            bank.setAccounts(accounts);
        } else {
            bank.getAccounts().add(createdAccount);
            logger.info("New registered account is added into bank.");
        }
        bankRepository.save(bank);

        return createdAccount;
    }

    private String generateNewMerchantPassword(String name, String merchantID) {
        String merchantPassword = name + merchantID + RandomStringUtils.randomAlphabetic(8);

        // Hash merchant password
        String hashedMerchantPassword = new BCryptPasswordEncoder().encode(merchantPassword);
        return hashedMerchantPassword.substring(0, 30);
    }

}
