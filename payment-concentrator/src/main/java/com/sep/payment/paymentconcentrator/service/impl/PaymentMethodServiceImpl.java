package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.Client;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.repository.ClientRepository;
import com.sep.payment.paymentconcentrator.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Set<PaymentMethod> getPaymentMethods(Long clientId) {
        Client client = clientRepository.getOne(clientId);
        return client.getPaymentMethods();
    }
}
