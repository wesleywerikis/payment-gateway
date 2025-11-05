package br.com.payments.antifraud.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    @Value("${app.rabbit.queues.toVerify}")
    String qToVerify;
    @Value("${app.rabbit.queues.status}")
    String qStatus;
    @Value("${app.rabbit.rk.toVerify}")
    String rkToVerify;
    @Value("${app.rabbit.rk.status}")
    String rkStatus;

    @Bean
    TopicExchange paymentsExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    Queue toVerifyQueue() {
        return QueueBuilder.durable(qToVerify).build();
    }

    @Bean
    Queue statusQueue() {
        return QueueBuilder.durable(qStatus).build();
    }

    @Bean
    Binding bindToVerify(@Qualifier("toVerifyQueue") Queue toVerifyQueue, TopicExchange paymentsExchange) {
        return BindingBuilder.bind(toVerifyQueue).to(paymentsExchange).with(rkToVerify);
    }

    @Bean
    Binding bindStatus(@Qualifier("statusQueue") Queue statusQueue, TopicExchange paymentsExchange) {
        return BindingBuilder.bind(statusQueue).to(paymentsExchange).with(rkStatus);
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }
}
