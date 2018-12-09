package com.sep.pcc.paymentcardcentre.entity.dto;



import java.util.Date;

public class AcquirerDataDTO {

    private Long acquirerOrderId;
    private Date acquirerTimestamp;
    private double amount;
    private CardDTO cardDTO;

    // dodala zbog cuvanja transakcije u banci kupca
    private Long merchantOrderId;
    private Long paymentId;

    public AcquirerDataDTO() {
    }

    public AcquirerDataDTO(Long acquirerOrderId, Date acquirerTimestamp, CardDTO card, double amount, Long merchantOrderId, Long paymentId) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.cardDTO = card;
        this.amount = amount;
        this.merchantOrderId = merchantOrderId;
        this.paymentId = paymentId;
    }

    public Long getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(Long acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public Date getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(Date acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public CardDTO getCard() {
        return cardDTO;
    }

    public void setCard(CardDTO card) {
        this.cardDTO = card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CardDTO getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
