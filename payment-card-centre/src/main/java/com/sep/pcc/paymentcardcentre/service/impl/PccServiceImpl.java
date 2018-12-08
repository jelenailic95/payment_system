package com.sep.pcc.paymentcardcentre.service.impl;

import com.sep.pcc.paymentcardcentre.entity.Bank;
import com.sep.pcc.paymentcardcentre.repository.PccRepository;
import com.sep.pcc.paymentcardcentre.service.PccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PccServiceImpl implements PccService {

    @Autowired
    private PccRepository pccRepository;

    @Override
    public Bank findBank(String pan) {
        int bankNumber = Integer.parseInt(pan.substring(0,6));
        return pccRepository.findByBankNumber(bankNumber);
    }
}
