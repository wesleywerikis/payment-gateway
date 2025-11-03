package br.com.payments.gateway.messaging;

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
    @Value("${app.rabbit.queues.created}")
    String qCreated;
    @Value("${app.rabbit.queues.status}")
    String qStatus;
    @Value("${app.rabbit.queues.toVerify}")
    String qToVerify;
    @Value("${app.rabbit.rk.created}")
    String rkCreated;
    @Value("${app.rabbit.rk.status}")
    String rkStatus;
    @Value("${app.rabbit.rk.toVerify}")
    String rkToverify;

    @Bean
    TopicExchange paymentsExchange() {
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
    Queue toVerifyQueue() {
        return QueueBuilder.durable(qToVerify).build();
    }

    @Bean
    Binding bindCreated(@Qualifier("createdQueue") Queue createdQueue, TopicExchange paymentsExchange) {
        return BindingBuilder.bind(createdQueue).to(paymentsExchange).with(rkCreated);
    }

    @Bean
    Binding bindStatus(@Qualifier("statusQueue") Queue statusQueue, TopicExchange paymentsExchange) {
        return BindingBuilder.bind(statusQueue).to(paymentsExchange).with(rkStatus);
    }

    @Bean
    Binding bindToVerify(@Qualifier("toVerifyQueue") Queue toVerifyQueue, TopicExchange paymentsExchange) {
        return BindingBuilder.bind(toVerifyQueue).to(paymentsExchange).with(rkToverify);
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }

}
