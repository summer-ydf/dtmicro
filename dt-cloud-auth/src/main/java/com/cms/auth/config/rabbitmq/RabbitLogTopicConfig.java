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
 * @author DT辰白 Created by 2022/1/25 14:40
 */
@Configuration
public class RabbitLogTopicConfig {

    /**
     * 声明死信队列
     * @return 返回
     */
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(RABBITMQ_LOG_DEAD_LETTER_QUEUE).build();
    }

    /**
     * 声明死信exchange
     * @return 返回
     */
    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.topicExchange(RABBITMQ_LOG_DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    /**
     * 绑定死信交换机
     * @return 返回
     */
    @Bean
    public Binding bindDeadExchange() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(RABBITMQ_LOG_DEAD_LETTER_ROUTING_KEY).noargs();
    }

    /**
     * 声明业务队列
     * @return 返回
     */
    @Bean
    public Queue topicQueue() {
        return QueueBuilder
                .durable(RABBITMQ_LOG_TOPIC_QUEUE)
                // 声明该队列绑定的死信交换机
                .withArgument("x-dead-letter-exchange", RABBITMQ_LOG_DEAD_LETTER_EXCHANGE)
                // 声明该队列的死信路由键
                .withArgument("x-dead-letter-routing-key", RABBITMQ_LOG_DEAD_LETTER_ROUTING_KEY)
                // 声明该队列的消息的过期时间,消息过期未被消费，则投放到绑定的死信队列中
                //.withArgument("x-message-ttl", 5000)
                .build();
    }

    /**
     * 声明业务exchange
     * @return 返回
     */
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(RABBITMQ_LOG_TOPIC_EXCHANGE).durable(true).build();
    }

    /**
     * 绑定业务交换机
     * @param topicQueue 业务队列
     * @param topicExchange 业务交换机
     * @return 返回
     */
    @Bean
    public Binding bindingTopicExchange(Queue topicQueue, Exchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with(RABBITMQ_LOG_TOPIC_ROUTING_KEY).noargs();
    }
}
