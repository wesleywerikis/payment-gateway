package br.com.payments.gateway.messaging;

import br.com.payments.contracts.events.PaymentStatusEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StatusPublisher {
    private final RabbitTemplate rabbit;
    private final String exchange;
    private final String rkStatus;

    public StatusPublisher(RabbitTemplate rabbit,
                           @Value("${app.rabbit.exchange}") String exchange,
                           @Value("${app.rabbit.rk.status}") String rkStatus) {
        this.rabbit = rabbit;
        this.exchange = exchange;
        this.rkStatus = rkStatus;
    }

    public void publish(PaymentStatusEvent evt) {
        rabbit.convertAndSend(exchange, rkStatus, evt);
    }
}
