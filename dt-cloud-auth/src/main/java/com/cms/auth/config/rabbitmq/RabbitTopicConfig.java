package com.cms.auth.config.rabbitmq;

import com.cms.common.tool.constant.ConstantCode;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ydf Created by 2022/1/25 14:40
 */
@Configuration
public class RabbitTopicConfig {

    @Bean
    public Queue topicQueue() {
        return new Queue(ConstantCode.RABBITMQ_TOPIC_LOG);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(ConstantCode.RABBITMQ_EXCHANGE_LOG);
    }

    @Bean
    Binding bindingExchange() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(ConstantCode.RABBITMQ_TOPIC_LOG);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        /**
         * 如果不自己创建amqpAdmin，自动配置类中会创建
         */
        AmqpAdmin amqpAdmin =  new RabbitAdmin(connectionFactory);
        amqpAdmin.declareQueue(topicQueue());
        /*
         * Exchange是一个接口实现类如下：
         * 	1.DirectExchange
         * 	2.FanoutExchange
         * 	3.TopicExchange
         * */
        amqpAdmin.declareExchange(topicExchange());
        /**
         * Binding可以设置的参数：
         * 	destination  目的地
         * 	destinationType 绑定的类型 是跟队列绑定 还是交换机绑定
         * 	exchange 交换机的name
         * 	routingKey 路由的key
         * 	arguments; 参数 可以不设置
         */
        amqpAdmin.declareBinding(bindingExchange());
        return amqpAdmin;
    }

}
