package com.sep.pcc.paymentcardcentre.entity.dto;

import java.util.Date;

public class TransactionDTO {

    private Long merchantOrderId;
    private String paymentId;
    private String status;
    private Long acquierOrderId;
    private Date acquirerTimestamp;

    public TransactionDTO(Long merchantOrderId, String paymentId, Long acquierOrderId, Date acquirerTimestamp) {
        this.merchantOrderId = merchantOrderId;
        this.paymentId = paymentId;
        this.acquierOrderId = acquierOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public TransactionDTO() {
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Long getAcquierOrderId() {
        return acquierOrderId;
    }

    public void setAcquierOrderId(Long acquierOrderId) {
        this.acquierOrderId = acquierOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }
}

