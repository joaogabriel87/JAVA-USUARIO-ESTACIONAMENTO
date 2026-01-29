package com.user.users_parking.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig {

    public static final String USER_QUEUE = "userQueue";
    public static final String VEHICLE_QUEUE = "vehicleQueue";
    public static final String EXCHANGE = "parkingExchange";
    public static final String RETRY_EXCHANGE = "parkingRetryExchange";
    public static final String DLQ_EXCHANGE = "parkingDlqExchange";

    @Bean
    public DirectExchange retryExchange() {
        return new DirectExchange(RETRY_EXCHANGE);
    }

    @Bean
    public DirectExchange dlqExchange() {
        return new DirectExchange(DLQ_EXCHANGE);
    }

    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(USER_QUEUE)
                .deadLetterExchange(RETRY_EXCHANGE)
                .deadLetterRoutingKey("user.retry").build();
    }

    @Bean
    public Queue vehicleQueue() {
        return QueueBuilder.durable(VEHICLE_QUEUE)
                .deadLetterExchange(RETRY_EXCHANGE)
                .deadLetterRoutingKey("vehicle.retry").build();
    }

    @Bean
    public Queue userRetryQueue() {
        return QueueBuilder.durable("userQueue.retry")
                .ttl(10000)
                .deadLetterExchange(EXCHANGE)
                .deadLetterRoutingKey("userRoutingKey").build();
    }
    @Bean
    public Queue vehicleRetryQueue() {
        return QueueBuilder.durable("vehicleQueue.retry")
                .ttl(10000)
                .deadLetterExchange(EXCHANGE)
                .deadLetterRoutingKey("vehicleRoutingKey").build();
    }

    @Bean Queue userDlqQueue() {
        return QueueBuilder.durable("userQueue.dlq").build();
    }

    @Bean Queue vehicleDlqQueue() {
        return QueueBuilder.durable("vehicleQueue.dlq").build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding userBinding(Queue userQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with("userRoutingKey");
    }

    @Bean
    public Binding vehicleBinding(Queue vehicleQueue, DirectExchange exchange) {
        return BindingBuilder.bind(vehicleQueue).to(exchange).with("vehicleRoutingKey");
    }

    @Bean
    public Binding userDlqBinding() {
        return BindingBuilder
                .bind(userDlqQueue())
                .to(dlqExchange())
                .with("user.dlq");
    }

    @Bean
    public Binding vehicleDlqBinding() {
        return BindingBuilder
                .bind(vehicleDlqQueue())
                .to(dlqExchange())
                .with("vehicle.dlq");
    }


    @Bean
    public Binding userRetryBinding() {
        return BindingBuilder
                .bind(userRetryQueue())
                .to(retryExchange())
                .with("user.retry");
    }

    @Bean
    public Binding vehicleRetryBinding() {
        return BindingBuilder
                .bind(vehicleRetryQueue())
                .to(retryExchange())
                .with("vehicle.retry");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}