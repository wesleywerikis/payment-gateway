package br.com.payments.gateway.messaging;

import br.com.payments.contracts.enums.PaymentStatus;
import br.com.payments.contracts.enums.ReasonCode;
import br.com.payments.contracts.events.PaymentCreatedEvent;
import br.com.payments.contracts.events.PaymentStatusEvent;
import br.com.payments.gateway.domain.GatewayDecisionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CreatedListener {
    private final StatusPublisher statusPublisher;
    private final ToVerifyPublisher toVerifyPublisher;
    private final GatewayDecisionService decision = new GatewayDecisionService();

    public CreatedListener(StatusPublisher statusPublisher, ToVerifyPublisher toVerifyPublisher) {
        this.statusPublisher = statusPublisher;
        this.toVerifyPublisher = toVerifyPublisher;
    }

    @RabbitListener(queues = "${app.rabbit.queues.created}")
    public void onCreated(PaymentCreatedEvent evt) {
        var d = decision.decide(evt);
        switch (d.kind) {
            case APPROVE -> statusPublisher.publish(
                    new PaymentStatusEvent(evt.getPaymentId(), PaymentStatus.APPROVED, ReasonCode.NONE, Instant.now()));
            case REQUIRE_ANTIFRAUD -> toVerifyPublisher.publish(evt);
            case ERROR -> statusPublisher.publish(
                    new PaymentStatusEvent(evt.getPaymentId(), PaymentStatus.ERROR, d.reason, Instant.now()));

        }
    }
}
