package com.sep.payment.paymentconcentrator.service.impl;

import com.sep.payment.paymentconcentrator.domain.entity.PaymentClient;
import com.sep.payment.paymentconcentrator.domain.entity.PaymentMethod;
import com.sep.payment.paymentconcentrator.repository.PaymentClientRepository;
import com.sep.payment.paymentconcentrator.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentClientRepository paymentClientRepository;

    @Override
    public Set<PaymentMethod> getPaymentMethods(Long clientId){
        PaymentClient pk = paymentClientRepository.getOne(clientId);
        return pk.getPaymentMethods();
    }
}
