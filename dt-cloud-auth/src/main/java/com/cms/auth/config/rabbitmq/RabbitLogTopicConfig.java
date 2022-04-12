package com.cms.auth.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cms.common.tool.constant.ConstantCode.RABBITMQ_LOG_DEAD_LETTER_EXCHANGE;
import static com.cms.common.tool.constant.ConstantCode.RABBITMQ_LOG_DEAD_LETTER_QUEUE;
import static com.cms.common.tool.constant.ConstantCode.RABBITMQ_LOG_DEAD_LETTER_ROUTING_KEY;
import static com.cms.common.tool.constant.ConstantCode.RABBITMQ_LOG_TOPIC_EXCHANGE;
import static com.cms.common.tool.constant.ConstantCode.RABBITMQ_LOG_TOPIC_QUEUE;
import static com.cms.common.tool.constant.ConstantCode.RABBITMQ_LOG_TOPIC_ROUTING_KEY;

/**
 * @author ydf Created by 2022/1/25 14:40
 */
@Configuration
public class RabbitLogTopicConfig {

    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(RABBITMQ_LOG_DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Queue deadExchange() {
        return ExchangeBuilder.topicExchange(RABBITMQ_LOG_DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding logDeadLetterBinding(Queue deadQueue, Exchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(RABBITMQ_LOG_DEAD_LETTER_ROUTING_KEY).noargs();
    }

    @Bean
    public Queue topicQueue() {
        return QueueBuilder
                .durable(RABBITMQ_LOG_TOPIC_QUEUE)
                // 声明该队列的死信交换机
                .withArgument("x-dead-letter-exchange", RABBITMQ_LOG_DEAD_LETTER_EXCHANGE)
                // 声明该队列死的死信路由键
                .withArgument("x-dead-letter-routing-key", RABBITMQ_LOG_DEAD_LETTER_ROUTING_KEY)
                // 声明该队列的消息的过期时间,消息过期未被消费，则投放到绑定的死信队列中
                //.withArgument("x-message-ttl", 5000)
                .build();
    }

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(RABBITMQ_LOG_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    Binding bindingExchange(Queue topicQueue, Exchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with(RABBITMQ_LOG_TOPIC_ROUTING_KEY).noargs();
    }
}
