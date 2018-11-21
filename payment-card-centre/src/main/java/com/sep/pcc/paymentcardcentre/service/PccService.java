package com.sep.pcc.paymentcardcentre.service;

import com.sep.pcc.paymentcardcentre.entity.Bank;
import com.sep.pcc.paymentcardcentre.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PccService {

    @Autowired
    private Repository repository;

    private Bank findBank(){
        return null;
    }
}
