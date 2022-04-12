package com.cms.auth.config.rabbitmq;

import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ydf Created by 2022/1/25 15:02
 */
@Configuration
public class RabbitCallbackConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                SysCmsUtils.log.error("消息发送失败!");
                // TODO 发送失败，可以记录失败日志
            }
            SysCmsUtils.log.info("RabbitMQ消息投递成功!!!");
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 当队列不存在，或者匹配失败的时候触发该回调函数
            SysCmsUtils.log.info("ReturnCallback:     "+"消息："+message);
            SysCmsUtils.log.info("ReturnCallback:     "+"回应码："+replyCode);
            SysCmsUtils.log.info("ReturnCallback:     "+"回应信息："+replyText);
            SysCmsUtils.log.info("ReturnCallback:     "+"交换机："+exchange);
            SysCmsUtils.log.info("ReturnCallback:     "+"路由键："+routingKey);
        });
        return rabbitTemplate;
    }

}
