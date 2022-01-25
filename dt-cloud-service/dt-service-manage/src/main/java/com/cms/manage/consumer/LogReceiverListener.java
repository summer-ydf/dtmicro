package com.cms.manage.consumer;

import com.cms.common.utils.SysCmsUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitHandler
    @RabbitListener(queues = "topic.cms.log")
    public void process(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        SysCmsUtils.log.info("deliveryTag->>>"+deliveryTag);
        try {
            byte[] body = message.getBody();
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(body));
            Map<String,String> msgMap = (Map<String, String>) inputStream.readObject();
            SysCmsUtils.log.info("添加日志信息记录成功->>>"+msgMap);
            // 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            // 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
            SysCmsUtils.log.error("添加日志信息记录失败->>>"+message);
            channel.basicReject(deliveryTag, false);
            // TODO 将数据放入死信队列
            e.printStackTrace();
        }
    }

}