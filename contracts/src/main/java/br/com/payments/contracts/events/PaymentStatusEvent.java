package br.com.payments.contracts.events;

import br.com.payments.contracts.enums.PaymentStatus;
import br.com.payments.contracts.enums.ReasonCode;

import java.time.Instant;

public class PaymentStatusEvent {
    private String paymentId;
    private PaymentStatus status;
    private ReasonCode reasonCode;
    private Instant at;

    public PaymentStatusEvent() {

    }

    public PaymentStatusEvent(String paymentId, PaymentStatus status, ReasonCode reasonCode, Instant at) {
        this.paymentId = paymentId;
        this.status = status;
        this.reasonCode = reasonCode;
        this.at = at;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public ReasonCode getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(ReasonCode reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Instant getAt() {
        return at;
    }

    public void setAt(Instant at) {
        this.at = at;
    }
}
