package br.com.payments.antifraud.messaging;

import br.com.payments.antifraud.domain.RulesEngine;
import br.com.payments.contracts.enums.PaymentStatus;
import br.com.payments.contracts.enums.ReasonCode;
import br.com.payments.contracts.events.PaymentCreatedEvent;
import br.com.payments.contracts.events.PaymentStatusEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ToVerifyListener {
    private final StatusPublisher publisher;
    private final RulesEngine engine = new RulesEngine();

    public ToVerifyListener(StatusPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = "${app.rabbit.queues.toVerify}")
    public void onToVerify(PaymentCreatedEvent evt) {
        var res = engine.evaluate(evt.getCardToken(), evt.getAmount(), evt.getMerchantId());
        if (res.verdict == RulesEngine.Result.Verdict.APPROVE) {
            publisher.publish(new PaymentStatusEvent(evt.getPaymentId(), PaymentStatus.APPROVED, ReasonCode.NONE, Instant.now()));
        } else {
            publisher.publish(new PaymentStatusEvent(evt.getPaymentId(), PaymentStatus.DECLINED, res.reason, Instant.now()));
        }

    }
}
