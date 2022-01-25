package com.cms.manage.consumer;

import com.cms.common.entity.SysLoginLogVo;
import com.cms.common.utils.SysCmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ydf Created by 2022/1/25 14:53
 */
@Component
@RabbitListener(queues = "topic.cms.log")
public class LogReceiverListener {

    @RabbitHandler
    public void process(SysLoginLogVo buildObject) {
        SysCmsUtils.log.info("添加日志信息记录->>>"+buildObject);
    }
}
