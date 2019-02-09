package com.sep.paypal.model.enumeration;

import com.paypal.api.payments.Agreement;
import lombok.Data;

@Data
public class AgreementWithPayee {

    private Agreement agreement;
    private String payee;



}
