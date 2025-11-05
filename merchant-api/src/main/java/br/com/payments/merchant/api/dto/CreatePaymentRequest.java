package br.com.payments.merchant.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreatePaymentRequest {

    @NotNull(message = "O campo 'amount' é obrigatório.")
    @Positive(message = "O campo 'amount' deve ser positivo.")
    private BigDecimal amount;
    @NotBlank(message = "O campo 'currency' é obrigatório.")
    private String currency;
    @NotBlank(message = "O campo 'cardToken' não pode estar vazio.")
    private String cardToken;
    @NotBlank(message = "O campo 'merchantId' é obrigatório.")
    private String merchantId;

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
