package com.cms.manage.consumer;

import com.alibaba.fastjson.JSON;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.manage.entity.SysLogLoginEntity;
import com.cms.manage.service.SysLogLoginService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author ydf Created by 2022/1/25 14:53
 */
@Component
public class LogReceiverListener {

    @Autowired
    private SysLogLoginService logService;

    @RabbitHandler
    @RabbitListener(queues = "log-topic-queue")
    public void process(@Payload String data, Channel channel, Message message) throws IOException {
        try {
            SysLogLoginEntity loginLogEntity = JSON.parseObject(data, SysLogLoginEntity.class);
            logService.saveLoginLog(loginLogEntity);
            // 消息确认手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            SysCmsUtils.log.info("添加日志信息记录成功->>>"+loginLogEntity);
        } catch (Exception e) {
            // 消息拒绝签收
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            SysCmsUtils.log.info("程序出错，投递到死信队列,路由键为:[{}]", message.getMessageProperties().getReceivedRoutingKey());
        }
    }

    @RabbitListener(queues = "log-dead-letter-queue")
    public void userDeadLetterConsumer(@Payload String data, Channel channel, Message message) throws IOException {
        SysCmsUtils.log.info("死信队列接受到消息:[{}]", data);
        // TODO 重新消费消息或者人工干预数据，重新消费
        SysLogLoginEntity loginLogEntity = JSON.parseObject(data, SysLogLoginEntity.class);
        logService.saveLoginLog(loginLogEntity);
        // 消息确认手动ACK
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        SysCmsUtils.log.info("添加日志信息记录成功->>>"+loginLogEntity);
    }
}
