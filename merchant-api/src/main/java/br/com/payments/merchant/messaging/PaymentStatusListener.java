package br.com.payments.merchant.messaging;

import br.com.payments.contracts.events.PaymentStatusEvent;
import br.com.payments.merchant.domain.PaymentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusListener {
    private final PaymentRepository repo;

    public PaymentStatusListener(PaymentRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = "${app.rabbit.queues.status}")
    public void onStatus(PaymentStatusEvent evt) {
        repo.findById(evt.getPaymentId()).ifPresent(p -> {
            p.setStatus(evt.getStatus());
            repo.save(p);
        });
    }
}
