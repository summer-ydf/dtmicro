package com.cms.auth.config.rabbitmq;

import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

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

        // 当交换机不存在，或者交换机队列绑定失败的时候触发该回调函数
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            int publishStatus = 1;
            if (!ack) {
                SysCmsUtils.log.error("RabbitMQ消息投递失败!!!");
                publishStatus = 2;
            }
            // 发送成功，记录发送日志
            assert correlationData != null;
            SysMqMessageVoEntity mqMessageVoEntity = SysMqMessageVoEntity.builder()
                    .messageId(correlationData.getId())
                    .title("发送登录日志消息")
                    .publishDate(new Date())
                    .publishStatus(publishStatus)
                    .message(cause)
                    .build();
            // TODO RPC远程调用保存数据
            SysCmsUtils.log.info("RabbitMQ消息投递成功!!!");
            SysCmsUtils.log.info("记录发送消息日志："+mqMessageVoEntity);
        });

        // 当队列不存在，或者匹配失败的时候触发该回调函数
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            SysCmsUtils.log.info("ReturnCallback:     "+"消息："+message);
            SysCmsUtils.log.info("ReturnCallback:     "+"回应码："+replyCode);
            SysCmsUtils.log.info("ReturnCallback:     "+"回应信息："+replyText);
            SysCmsUtils.log.info("ReturnCallback:     "+"交换机："+exchange);
            SysCmsUtils.log.info("ReturnCallback:     "+"路由键："+routingKey);
        });
        return rabbitTemplate;
    }

}
