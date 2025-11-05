package br.com.payments.merchant.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbit.exchange}")
    String exchangeName;
    @Value("${app.rabbit.queues.created}")
    String qCreated;
    @Value("${app.rabbit.queues.status}")
    String qStatus;
    @Value("${app.rabbit.rk.created}")
    String rkCreated;
    @Value("${app.rabbit.rk.status}")
    String rkStatus;

    @Bean
    TopicExchange paymentExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    Queue createdQueue() {
        return QueueBuilder.durable(qCreated).build();
    }

    @Bean
    Queue statusQueue() {
        return QueueBuilder.durable(qStatus).build();
    }

    @Bean
    Binding bindCreated(@Qualifier("createdQueue") Queue createdQueue,
                        TopicExchange paymentsExchange,
                        @Value("${app.rabbit.rk.created}") String rkCreated) {
        return BindingBuilder.bind(createdQueue).to(paymentsExchange).with(rkCreated);
    }

    @Bean
    Binding bindStatus(@Qualifier("statusQueue") Queue statusQueue,
                       TopicExchange paymentsExchange,
                       @Value("${app.rabbit.rk.status}") String rkStatus) {
        return BindingBuilder.bind(statusQueue).to(paymentsExchange).with(rkStatus);
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
