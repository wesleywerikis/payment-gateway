package br.com.payments.gateway.messaging;

import br.com.payments.contracts.events.PaymentCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ToVerifyPublisher {
    private final RabbitTemplate rabbit;
    private final String exchange;
    private final String rkToVerify;

    public ToVerifyPublisher(RabbitTemplate rabbit,
                             @Value("${app.rabbit.exchange}") String exchange,
                             @Value("${app.rabbit.rk.toVerify}") String rkToVerify) {
        this.rabbit = rabbit;
        this.exchange = exchange;
        this.rkToVerify = rkToVerify;
    }

    public void publish(PaymentCreatedEvent evt) {
        rabbit.convertAndSend(exchange, rkToVerify, evt);
    }
}
