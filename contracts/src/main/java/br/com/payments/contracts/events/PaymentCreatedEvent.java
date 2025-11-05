package br.com.payments.contracts.events;

import java.math.BigDecimal;

public class PaymentCreatedEvent {
    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String cardToken;
    private String merchantId;

    public PaymentCreatedEvent() {

    }

    public PaymentCreatedEvent(String paymentId, BigDecimal amount, String currency, String cardToken, String merchantId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.currency = currency;
        this.cardToken = cardToken;
        this.merchantId = merchantId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
