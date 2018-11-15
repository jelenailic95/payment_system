package com.sep.bank.bankservice.entity.dto;

import java.util.Date;

public class IssuerDataDTO {

    private int issuerOrderId;
    private Date issuerTimestamp;

    public IssuerDataDTO() {
    }

    public IssuerDataDTO(int issuerOrderId, Date issuerTimestamp) {
        this.issuerOrderId = issuerOrderId;
        this.issuerTimestamp = issuerTimestamp;
    }

    public int getIssuerOrderId() {
        return issuerOrderId;
    }

    public void setIssuerOrderId(int issuerOrderId) {
        this.issuerOrderId = issuerOrderId;
    }

    public Date getIssuerTimestamp() {
        return issuerTimestamp;
    }

    public void setIssuerTimestamp(Date issuerTimestamp) {
        this.issuerTimestamp = issuerTimestamp;
    }
}
