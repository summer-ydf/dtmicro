package com.cms.manage.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.manage.entity.SysLogLoginEntity;
import com.cms.manage.service.SysLogLoginService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @author ydf Created by 2022/1/25 14:53
 */
@Component
public class LogReceiverListener {

    @Autowired
    private SysLogLoginService logService;

    @RabbitHandler
    @RabbitListener(queues = "log-topic-queue")
    public void process(@Payload String data, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        SysCmsUtils.log.info("deliveryTag->>>"+deliveryTag);
        SysCmsUtils.log.info("data->>>"+data);
//        try {
//            byte[] body = message.getBody();
//            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(body));
//            Object object = inputStream.readObject();
//            Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(object));
//            SysLogLoginEntity loginLogEntity = JSON.parseObject(map.get("data").toString(), SysLogLoginEntity.class);
//            logService.saveLoginLog(loginLogEntity);
//            SysCmsUtils.log.info("添加日志信息记录成功->>>"+loginLogEntity);
//            // 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//            channel.basicAck(deliveryTag, true);
//        } catch (Exception e) {
//            // 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
//            SysCmsUtils.log.error("添加日志信息记录失败->>>"+message);
//            channel.basicReject(deliveryTag, false);
//            // TODO 将数据放入死信队列
//            e.printStackTrace();
//        }
        throw new RuntimeException("模拟发生异常");
    }

    @RabbitListener(queues = "log-dead-letter-queue")
    public void userDeadLetterConsumer(@Payload String data, Channel channel, Message message) {
        SysCmsUtils.log.info("接收到死信消息:[{}]", data);
    }
}
