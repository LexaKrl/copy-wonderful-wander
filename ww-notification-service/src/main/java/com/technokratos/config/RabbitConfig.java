package com.technokratos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technokratos.util.RabbitUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    @Bean
    DirectExchange notificationExchange() {
        return new DirectExchange(RabbitUtilities.NOTIFICATION_EXCHANGE);
    }

    @Bean
    Queue walkNotificationQueue() {
        return QueueBuilder
                .durable(RabbitUtilities.WALK_FINISHED_QUEUE)
                .build();
    }

    @Bean
    Binding walkNotificationBinding() {
        return BindingBuilder
                .bind(walkNotificationQueue())
                .to(notificationExchange())
                .with(RabbitUtilities.WALK_FINISHED_QUEUE);
    }

    /* TODO define other parts of rabbit structure */

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }
}
