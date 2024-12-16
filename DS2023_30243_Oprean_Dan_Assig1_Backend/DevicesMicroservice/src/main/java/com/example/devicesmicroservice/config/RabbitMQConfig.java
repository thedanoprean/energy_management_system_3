package com.example.devicesmicroservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "Device_Service_Exchange";
    public static final String ROUTING_KEYS = "Device_Service_Routing_Key";
    public static final String QUEUE = "Device_Service_Queue";

    @Bean
    public Queue deviceQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingDeviceServiceQueue(Queue deviceQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deviceQueue).to(exchange).with(ROUTING_KEYS);
    }
}

