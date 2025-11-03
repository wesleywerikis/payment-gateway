package br.com.payments.merchant.domain;

import br.com.payments.contracts.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Payment {

    @Id
    private String id = UUID.randomUUID().toString();
    private BigDecimal amount;
    private String currency;
    private String merchantId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public Payment() {

    }

    public Payment(BigDecimal amount, String currency, String merchantId) {
        this.amount = amount;
        this.currency = currency;
        this.merchantId = merchantId;
    }

    @PreUpdate
    public void touch() {
        this.updatedAt = Instant.now();
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

    public void setStatus(PaymentStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();

    }
}
