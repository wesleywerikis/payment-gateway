package br.com.payments.merchant.api.dto;

import br.com.payments.contracts.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentResponse {

    private String id;
    private BigDecimal amount;
    private String currency;
    private String merchantId;
    private PaymentStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public PaymentResponse() {

    }

    public PaymentResponse(String id, BigDecimal amount, String currency, String merchantId, PaymentStatus status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.merchantId = merchantId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
