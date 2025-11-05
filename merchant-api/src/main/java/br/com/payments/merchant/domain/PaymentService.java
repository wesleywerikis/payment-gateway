package br.com.payments.merchant.domain;

import br.com.payments.contracts.events.PaymentCreatedEvent;
import br.com.payments.merchant.events.PaymentEventsPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository repo;
    private final PaymentEventsPublisher publisher;

    public PaymentService(PaymentRepository repo, PaymentEventsPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Transactional
    public Payment createAndPublish(BigDecimal amount, String currency, String cardToken, String merchantId) {
        Payment p = new Payment(amount, currency, merchantId);
        repo.save(p);
        publisher.publishCreated(new PaymentCreatedEvent(p.getId(), amount, currency, cardToken, merchantId));
        return p;
    }

    public Optional<Payment> findById(String id) {
        return repo.findById(id);
    }

    public Payment save(Payment p) {
        return repo.save(p);
    }
}
