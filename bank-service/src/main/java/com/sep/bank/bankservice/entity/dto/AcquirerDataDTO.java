package com.sep.bank.bankservice.entity.dto;

import com.sep.bank.bankservice.entity.Card;

import java.util.Date;

public class AcquirerDataDTO {

    private int acquirerOrderId;
    private Date acquirerTimestamp;
    private Card card;

    public AcquirerDataDTO() {
    }

    public AcquirerDataDTO(int acquirerOrderId, Date acquirerTimestamp, Card card) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.card = card;
    }

    public int getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(int acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
