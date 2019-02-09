package com.sep.bank.bankservice.service.serviceImpl;

import com.sep.bank.bankservice.entity.*;
import com.sep.bank.bankservice.entity.dto.*;
import com.sep.bank.bankservice.repository.BankRepository;
import com.sep.bank.bankservice.repository.GeneralSequenceRepository;
import com.sep.bank.bankservice.repository.PaymentRequestRepository;
import com.sep.bank.bankservice.repository.TransactionRepository;
import com.sep.bank.bankservice.security.AES;
import com.sep.bank.bankservice.service.AccountService;
import com.sep.bank.bankservice.service.BankService;
import com.sep.bank.bankservice.service.CardService;
import com.sep.bank.bankservice.service.UserService;
import com.sep.bank.bankservice.util.FieldsGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Value("${pc.host}")
    private String pcHost;

    private final BankRepository bankRepository;

    private final AccountService accountService;

    private final UserService userService;

    private final CardService cardService;

    private final RestTemplate restTemplate;

    private final TransactionRepository transactionRepository;

    private final AES aes;

    private final GeneralSequenceRepository gsr;

    private FieldsGenerator fieldsGenerator;

    private Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, AccountService accountService, UserService userService,
                           CardService cardService, RestTemplate restTemplate,
                           TransactionRepository transactionRepository, AES aes, FieldsGenerator fieldsGenerator,
                           GeneralSequenceRepository gsr, PaymentRequestRepository paymentRequestRepository) {
        this.bankRepository = bankRepository;
        this.accountService = accountService;
        this.userService = userService;
        this.cardService = cardService;
        this.restTemplate = restTemplate;
        this.transactionRepository = transactionRepository;
        this.aes = aes;
        this.fieldsGenerator = fieldsGenerator;
        this.gsr = gsr;
        this.paymentRequestRepository = paymentRequestRepository;
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    /**
     * Create payment url and save payment request into db.
     *
     * @param requestDTO payment request
     * @return payment request data with the created url.
     */
    @Override
    public PaymentDataDTO getPaymentUrl(PaymentRequestDTO requestDTO) {
        String merchantId = aes.decrypt(requestDTO.getMerchantId());
        String merchantPassword = aes.decrypt(requestDTO.getMerchantPassword());

        Account account = accountService.checkMerchantData(merchantId, merchantPassword);
        PaymentDataDTO paymentDataDTO = new PaymentDataDTO();
        if (account != null) {
            logger.info("This client exists in the system. Merchant id: {}", account.getMerchantId());

            String paymentUrl = fieldsGenerator.generateField(Long.toString(requestDTO.getMerchantOrderId()), 256);

            // remove '/' from generated url
            paymentUrl = paymentUrl.replaceAll("/", "");

            String paymentId = RandomStringUtils.randomAlphabetic(10);   // generate payment id

            paymentDataDTO = new PaymentDataDTO(paymentUrl, paymentId, requestDTO.getAmount(),
                    requestDTO.getMerchantOrderId());

            // save payment request into db
            PaymentRequest paymentRequest = new PaymentRequest(requestDTO.getAmount(), account.getId(),
                    requestDTO.getMerchantOrderId(), paymentUrl, paymentId);
            paymentRequestRepository.save(paymentRequest);

            logger.info("Payment request saved into db.");
        }
        return paymentDataDTO;
    }

    /**
     * Check if card exists in the bank2 and process transaction.
     *
     * @param acquirerDataDTO acquirer data, card data and amount
     * @return transaction
     */
    @Override
    public Transaction checkCard(AcquirerDataDTO acquirerDataDTO) {
        CardDTO card = acquirerDataDTO.getCard();

        Card foundCard = cardService.findCard(card.getPan(), card.getSecurityCode(), card.getCardHolderName(),
                card.getExpirationDate(), true);

        if (foundCard != null) {
            logger.info("This credit card exists in the bank system. Card holder name: {}",
                    aes.decrypt(foundCard.getCardHolderName()));

            if (checkAmountOnAccount(foundCard, acquirerDataDTO.getAmount())) {
                logger.info("This client has enough money on the card. Transaction status: PAID.");
                return createTransaction(acquirerDataDTO, TransactionStatus.PAID);
            } else {
                logger.info("This client doesn't have enough money on the card. Status: REFUSED.");
                return createTransaction(acquirerDataDTO, TransactionStatus.REFUSED);
            }
        }
        // card is not found in the system, status is failed.
        logger.info("This credit card doesn't exist in the bank system. Transaction status: FAILED");
        return createTransaction(acquirerDataDTO, TransactionStatus.FAILED);
    }

    /**
     * Create transaction and save it into bank's 2 db.
     *
     * @param acquirerDataDTO acquirer data, card data and amount
     * @param status transaction status
     * @return create transaction
     */
    private Transaction createTransaction(AcquirerDataDTO acquirerDataDTO, TransactionStatus status) {
        Transaction transaction = new Transaction();
        transaction.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        transaction.setAmount(acquirerDataDTO.getAmount());
        transaction.setStatus(status);
        transaction.setMerchantOrderId(acquirerDataDTO.getMerchantOrderId());
        transaction.setPaymentId(acquirerDataDTO.getPaymentId());

        // if transaction is forwarded to the bank2, save it there as well
        transactionRepository.save(transaction);
        logger.info("New transaction is successfully created and saved.");
        return transaction;
    }

    /**
     * Pay buy card: check if card exists in the db, if not forward it to the PCC,
     * otherwise check if there is enough money on the card and process transaction.
     *
     * @param card credit card data with the amount
     * @return final transaction
     */
    @Override
    public Transaction checkBankForCard(CardAmountDTO card) {
        Card foundCard = cardService.findCard(card.getPan(), card.getSecurityCode(), card.getCardHolderName(),
                card.getExpirationDate(), false);

        Transaction transaction = new Transaction();
        transaction.setMerchantOrderId(card.getMerchantOrderId());
        transaction.setMerchantOrderId(card.getMerchantOrderId());
        transaction.setPaymentId(card.getPaymentId());
        transaction.setAmount(card.getAmount());

        // if credit card exist in this bank, check account balance
        if (foundCard != null) {
            logger.info("This credit card exists in the bank system. Card holder name: {}",
                    aes.decrypt(foundCard.getCardHolderName()));
            if (checkAmountOnAccount(foundCard, card.getAmount())) {
                logger.info("This client has enough money on the card. Transaction status: PAID.");
                transferMoneyToMerchantsAccount(transaction.getMerchantOrderId(), card.getAmount());
                transaction.setStatus(TransactionStatus.PAID);
            } else {
                logger.info("This client doesn't have enough money on the card. Status: REFUSED.");
                transaction.setStatus(TransactionStatus.REFUSED);
            }

        // banks are different, forward data to the PCC
        } else {
            logger.info("This credit card doesn't exist in the bank system. Transaction request is forwarded to the PCC.");
            transaction = forwardToPcc(card, transaction);
            return transaction;
        }
        Transaction transactionSaved = transactionRepository.save(transaction);
        logger.info("Transaction is successfully saved. Transaction: {}", transactionSaved.getId());
        return transaction;
    }

    /**
     * Forward transaction request to the PCC.
     *
     * @param cardAmountDTO card data with the amount
     * @param transaction create transaction
     * @return transaction
     */
    private Transaction forwardToPcc(CardAmountDTO cardAmountDTO, Transaction transaction) {
        CardDTO cardDTO = new CardDTO(aes.encrypt(cardAmountDTO.getPan()),
                aes.encrypt(cardAmountDTO.getSecurityCode()),
                aes.encrypt(cardAmountDTO.getCardHolderName()), aes.encrypt(cardAmountDTO.getExpirationDate()));

        // generate acquirer order id
        GeneralSequenceNumber gsn = gsr.getOne(1L);           // get acquirer counter
        gsn.setAcquirerCounter(gsn.getAcquirerCounter() + 1L);    // increment acquirer counter
        gsr.save(gsn);

        Long acquirerOrderId = gsn.getAcquirerCounter();

        AcquirerDataDTO acquirerDataDTO = new AcquirerDataDTO(acquirerOrderId, new Date(), cardDTO,
                transaction.getAmount(), transaction.getMerchantOrderId(), transaction.getPaymentId());

        transaction.setAcquirerTimestamp(acquirerDataDTO.getAcquirerTimestamp());
        transaction.setAcquirerOrderId(acquirerDataDTO.getAcquirerOrderId());
        logger.info("Transaction acquirer timestamp: {}", transaction.getAcquirerTimestamp());

        // poziva PCC koji prosledjuje podatke banci kupca i vraca status
        PaymentResultDTO paymentResultDTO = restTemplate.postForObject(pcHost + "/forward-to-bank",
                acquirerDataDTO, PaymentResultDTO.class);

        if (paymentResultDTO != null) {
            logger.info("Transaction result is returned from the client's bank. Transaction status: {}",
                    transaction.getStatus());
            transaction.setStatus(paymentResultDTO.getStatus());
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        // if transaction status is paid, transfer money to the merchant's account
        if (transaction.getStatus().equals(TransactionStatus.PAID)) {
            transferMoneyToMerchantsAccount(transaction.getMerchantOrderId(), cardAmountDTO.getAmount());
        }
        return transaction;
    }

    /**
     * Transfer money to the merchant's account.
     *
     * @param merchantOrderId merchant order id
     * @param amount amoutn
     */
    private void transferMoneyToMerchantsAccount(Long merchantOrderId, double amount) {
        PaymentRequest paymentRequest = paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
        Account account = accountService.getAccount(paymentRequest.getMerchantAccountId());
        account.setAmount(account.getAmount() + amount);
        accountService.saveAccount(account);
    }

    /**
     * Check if there is enough money on the credit card.
     * If there is enough money on the credit card, reduce the amount.
     *
     * @param foundCard found card object.
     * @param amount amount
     * @return true if there is enough money on the card, false otherwise
     */
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

    /**
     * Register new bank account.
     *
     * @param clientFullName client's full name
     * @param email client's email
     * @param bankName client's bank name
     * @return new account
     */
    @Override
    public Account registerNewAccount(String clientFullName, String email, String bankName) {
        Bank bank = bankRepository.findByName(bankName);
        User user = userService.create(new User(clientFullName, email));

        Account newAccount = new Account();
        newAccount.setCardHolder(user);

        // generate merchant id
        newAccount.setMerchantId(generateMerchantField(30));

        // generate merchant password
        newAccount.setMerchantPassword(generateMerchantField(100));

        newAccount.setAccountNumber(RandomStringUtils.randomAlphabetic(16));

        Account createdAccount = accountService.create(newAccount);
        logger.info("New account is successfully created and saved.");

        if (bank.getAccounts() == null) {
            logger.info("This bank doesn't have bank accounts. New account is the first one being added.");
            HashSet<Account> accounts = new HashSet<>();
            accounts.add(createdAccount);
            bank.setAccounts(accounts);
        } else {
            bank.getAccounts().add(createdAccount);
            logger.info("New account is added into bank db.");
        }
        bankRepository.save(bank);

        return createdAccount;
    }

    /**
     * Generate merchant field
     *
     * @param length field length
     * @return string-merchant field
     */
    private String generateMerchantField(int length) {
        String field = RandomStringUtils.randomAlphabetic(length);
        return aes.encrypt(field);
    }

    /**
     * Get issuer order id.
     *
     * @return issuer order id
     */
    @Override
    public Long getIssuerOrderId() {
        GeneralSequenceNumber gsn = gsr.getOne(1L);         // get issuer counter
        gsn.setIssuerCounter(gsn.getIssuerCounter() + 1L);      // increment issuer counter
        gsr.save(gsn);
        return gsn.getIssuerCounter();
    }

    /**
     * Get payment request for the given payment url.
     *
     * @param paymentUrl payment url
     * @return payment request
     */
    @Override
    public PaymentRequest getPaymentRequest(String paymentUrl) {
        PaymentRequest paymentRequest = paymentRequestRepository.findByPaymentUrl(paymentUrl);
        logger.info("Found payment request: {}", paymentRequest.toString());
        return paymentRequest;
    }

}
