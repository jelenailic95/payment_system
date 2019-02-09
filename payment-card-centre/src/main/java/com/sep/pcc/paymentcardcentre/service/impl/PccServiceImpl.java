package com.sep.pcc.paymentcardcentre.service.impl;

import com.sep.pcc.paymentcardcentre.entity.Bank;
import com.sep.pcc.paymentcardcentre.entity.PccRequest;
import com.sep.pcc.paymentcardcentre.entity.dto.AcquirerDataDTO;
import com.sep.pcc.paymentcardcentre.entity.dto.CardDTO;
import com.sep.pcc.paymentcardcentre.repository.PccRepository;
import com.sep.pcc.paymentcardcentre.repository.PccRequestRepository;
import com.sep.pcc.paymentcardcentre.security.AES;
import com.sep.pcc.paymentcardcentre.service.PccService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PccServiceImpl implements PccService {

    private PccRepository pccRepository;

    private PccRequestRepository pccRequestRepository;

    private AES aes;

    private Logger logger = LoggerFactory.getLogger(PccServiceImpl.class);

    @Autowired
    public PccServiceImpl(PccRepository pccRepository, PccRequestRepository pccRequestRepository, AES aes) {
        this.pccRepository = pccRepository;
        this.pccRequestRepository = pccRequestRepository;
        this.aes = aes;
    }

    @Override
    public Bank findBank(AcquirerDataDTO acquirerDataDTO) {
        CardDTO cardDTO = acquirerDataDTO.getCard();

        String panDecrypted = aes.decrypt(cardDTO.getPan());

        int bankNumber = Integer.parseInt(panDecrypted.substring(0, 6));

        Bank bank = pccRepository.findByBankIdentifier(bankNumber);

        PccRequest pccRequest = new PccRequest(acquirerDataDTO.getAcquirerTimestamp(),
                acquirerDataDTO.getAcquirerOrderId(), acquirerDataDTO.getAmount());

        if (bank != null) {
            logger.info("Forward to the bank: {}", bank.getBankName());
            pccRequest.setIssuerBank(bank.getBankName());
        } else {
            logger.info("PCC cannot recognize bank with the identifier {}", bankNumber);
        }

        pccRequestRepository.save(pccRequest);
        logger.info("PCC request is successfully created and saved.");

        return bank;
    }
}
