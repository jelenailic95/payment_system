package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentRequestRepository;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

    private final PaymentRequestRepository paymentRequestRepository;

    private final ClientRepository clientRepository;

    private Logger logger = LoggerFactory.getLogger(PaymentRequestServiceImpl.class);

    @Autowired
    public PaymentRequestServiceImpl(PaymentRequestRepository paymentRequestRepository, ClientRepository clientRepository) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Method creates payment request and stores its in the db.
     *
     * @param client journal name
     * @param amount paper or journal amount
     * @param bankName name of the bank
     * @param tokens array with the information of the paper/journal that is being bought
     * @return created payment request
     */
    @Override
    public PaymentRequest createPaymentRequest(String client, double amount, String bankName, String[] tokens) {
        // get client for the given bank name and the journal name
        Client foundClient = clientRepository.findByJournalAndPaymentMethodMethodName(client, bankName);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(foundClient.getClientId());
        paymentRequest.setMerchantPassword(foundClient.getClientPassword());
        paymentRequest.setAmount(amount);

        paymentRequest.setUsername(tokens[0]);
        paymentRequest.setJournalName(tokens[2]);
        paymentRequest.setTypeOfPayment(tokens[1]);
        paymentRequest.setScName(tokens[4]);

        // if client is buying paper, set paper id
        if(paymentRequest.getTypeOfPayment().equals("paper"))
            paymentRequest.setPaperId(Long.parseLong(tokens[5]));

        // get last payment request from the db
        PaymentRequest lastPayment = paymentRequestRepository.findTopByOrderByMerchantOrderIdDesc();

        // generate new merchant order id by incrementing the last stored merchant order id
        Long merchantOrderId = lastPayment.getMerchantOrderId() + 1;

        paymentRequest.setMerchantOrderId(merchantOrderId);

        // generate URLs
        String errorUrl = "error?order=" + merchantOrderId + "?" + RandomStringUtils.randomAlphabetic(16);
        String successUrl = "success?order=" + merchantOrderId + "?" + RandomStringUtils.randomAlphabetic(16);
        String failUrl = "fail?order=" + merchantOrderId + "?" + RandomStringUtils.randomAlphabetic(16);

        paymentRequest.setErrorUrl(errorUrl);
        paymentRequest.setSuccessUrl(successUrl);
        paymentRequest.setFaildUrl(failUrl);

        paymentRequestRepository.save(paymentRequest);

        logger.info("Payment request is successfully created.");
        logger.info("Payment request - merchant order: {}", paymentRequest.getMerchantOrderId());

        return paymentRequest;
    }

    /**
     * Method creates payment request and stores its in the db.
     *
     * @param username username
     * @param amount amount
     * @param journalName journal name
     * @param paperId paper id
     * @param typeOfPayment type of payment
     * @param scName clients name
     * @return created payment request
     */
    @Override
    public PaymentRequest createRequest(String username, double amount, String journalName, Long paperId, String typeOfPayment, String scName) {

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUsername(username);
        paymentRequest.setAmount(amount);
        paymentRequest.setJournalName(journalName);
        paymentRequest.setPaperId(paperId);
        paymentRequest.setTypeOfPayment(typeOfPayment);
        paymentRequest.setScName(scName);

        PaymentRequest lastPayment = paymentRequestRepository.findTopByOrderByMerchantOrderIdDesc();
        Long merchantOrderId = lastPayment.getMerchantOrderId() + 1;

        paymentRequest.setMerchantOrderId(merchantOrderId);
        paymentRequestRepository.save(paymentRequest);

        logger.info("Payment request is successfully created.");
        return paymentRequest;
    }

    /**
     * Get payment request.
     *
     * @param merchantOrderId merchant order id
     * @return payment request
     */
    @Override
    public PaymentRequest getPaymentRequest(Long merchantOrderId) {
        return paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
    }

    /**
     * Save payment request into db.
     *
     * @param paymentRequest payment request
     * @return  payment request
     */
    @Override
    public PaymentRequest save(PaymentRequest paymentRequest) {
        return paymentRequestRepository.save(paymentRequest);
    }

    /**
     * Get payment request from the db.
     *
     * @param id id
     * @return payment request
     */
    @Override
    public PaymentRequest getByIde(Long id) {
        return paymentRequestRepository.getOne(id);
    }
}
