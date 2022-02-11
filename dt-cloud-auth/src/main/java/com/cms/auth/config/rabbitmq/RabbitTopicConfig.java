package com.cms.auth.config.rabbitmq;

import com.cms.common.tool.constant.ConstantCommonCode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ydf Created by 2022/1/25 14:40
 */
@Configuration
public class RabbitTopicConfig {

    @Bean
    public Queue firstQueue() {
        return new Queue(ConstantCommonCode.RABBITMQ_TOPIC_LOG);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(ConstantCommonCode.RABBITMQ_EXCHANGE_LOG);
    }

    @Bean
    Binding bindingExchange() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(ConstantCommonCode.RABBITMQ_TOPIC_LOG);
    }

}
