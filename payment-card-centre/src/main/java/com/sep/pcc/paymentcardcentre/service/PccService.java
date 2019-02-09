package com.sep.pcc.paymentcardcentre.service;

import com.sep.pcc.paymentcardcentre.entity.Bank;
import com.sep.pcc.paymentcardcentre.entity.dto.AcquirerDataDTO;
import org.springframework.stereotype.Service;

@Service
public interface PccService {
    Bank findBank(AcquirerDataDTO acquirerDataDTO);
}
