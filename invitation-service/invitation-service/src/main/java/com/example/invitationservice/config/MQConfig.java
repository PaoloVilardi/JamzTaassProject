package com.example.invitationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String INV_LIST_UPDATE_QUEUE = "inv_list_update_queue";
    public static final String INV_LIST_UPDATE_EXCHANGE = "inv_list_update_exchange";
    public static final String INV_LIST_UPDATE_ROUTING_KEY = "inv_list_update_routingKey";

    public static final String APPLY_LIST_UPDATE_QUEUE = "apply_list_update_queue";
    public static final String APPLY_LIST_UPDATE_EXCHANGE = "apply_list_update_exchange";
    public static final String APPLY_LIST_UPDATE_ROUTING_KEY = "apply_list_update_routingKey";

    @Bean
    public Queue invListUpdateQueue() {
        return  new Queue(INV_LIST_UPDATE_QUEUE);
    }

    @Bean
    public TopicExchange invListUpdateExchange() {
        return new TopicExchange(INV_LIST_UPDATE_EXCHANGE);
    }

    @Bean
    public Binding invListUpdateBinding() {
        return BindingBuilder
                .bind(invListUpdateQueue())
                .to(invListUpdateExchange())
                .with(INV_LIST_UPDATE_ROUTING_KEY);
    }

    @Bean
    public Queue applyListUpdateQueue() {
        return  new Queue(APPLY_LIST_UPDATE_QUEUE);
    }

    @Bean
    public TopicExchange applyListUpdateExchange() {
        return new TopicExchange(APPLY_LIST_UPDATE_EXCHANGE);
    }

    @Bean
    public Binding applyListUpdateBinding() {
        return BindingBuilder
                .bind(applyListUpdateQueue())
                .to(applyListUpdateExchange())
                .with(APPLY_LIST_UPDATE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }

}
