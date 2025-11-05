package br.com.payments.merchant.events;

import br.com.payments.contracts.events.PaymentCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventsPublisher {

    private final RabbitTemplate rabbit;
    private final String exchange;
    private final String rkCreated;

    public PaymentEventsPublisher(RabbitTemplate rabbit,
                                  @Value("${app.rabbit.exchange}") String exchange,
                                  @Value("${app.rabbit.rk.created}") String rkCreated) {
        this.rabbit = rabbit;
        this.exchange = exchange;
        this.rkCreated = rkCreated;
    }

    public void publishCreated(PaymentCreatedEvent evt) {
        rabbit.convertAndSend(exchange, rkCreated, evt);
    }
}
