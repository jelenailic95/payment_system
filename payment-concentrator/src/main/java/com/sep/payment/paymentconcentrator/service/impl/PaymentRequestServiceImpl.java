package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentRequest;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.repository.PaymentRequestRepository;
import com.sep.payment.paymentconcentrator.service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Override
    public PaymentRequest createPaymentRequest(Long clientID, double amount) {
        Client client = clientRepository.getOne(clientID);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(client.getMerchantId());
        paymentRequest.setMerchantPassword(client.getMerchantPassword());

        Random random = new Random();
        paymentRequest.setMerchantOrderId(random.nextInt());
        paymentRequest.setAmount(amount);
        paymentRequest.setMerchandTimestamp(new Date());

        paymentRequestRepository.save(paymentRequest);

        return paymentRequest;
    }

    @Override
    public PaymentRequest getPaymentRequest(int merchantOrderId){
        return paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
    }


}
