package com.ms.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${notification.rabbitmq.queue}")
    private String queueName;

    @Value("${notification.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${notification.rabbitmq.routing-key}")
    private String routingKey;

    @Value("${notification.rabbitmq.dlq-queue}")
    private String dlqQueueName;

    @Value("${notification.rabbitmq.dlq-exchange}")
    private String dlqExchangeName;

    @Value("${notification.rabbitmq.dlq-routing-key}")
    private String dlqRoutingKey;

    // Main queue with Dead Letter Queue configured
    @Bean
    public Queue notificationQueue(){
        return QueueBuilder.durable(queueName)
                .deadLetterExchange(dlqExchangeName)
                .deadLetterRoutingKey(dlqRoutingKey)
                .build();
    }

    @Bean
    public DirectExchange notificationExchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding notificationBinding(){
        return BindingBuilder.bind(notificationQueue())
                .to(notificationExchange())
                .with(routingKey);
    }

    // Dead Letter Queue (DLQ)
    @Bean
    public Queue notificationDlqQueue() {
        return QueueBuilder.durable(dlqQueueName).build();
    }

    @Bean
    public DirectExchange notificationDlqExchange() {
        return new DirectExchange(dlqExchangeName);
    }

    @Bean
    public Binding notificationDlqBinding() {
        return BindingBuilder.bind(notificationDlqQueue())
                .to(notificationDlqExchange())
                .with(dlqRoutingKey);
    }

    // JSON converter to serialize/deserialize the RIGHTS in the queue.
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
