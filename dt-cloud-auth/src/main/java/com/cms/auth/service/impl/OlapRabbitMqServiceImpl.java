package com.cms.auth.service.impl;

import com.cms.auth.service.OlapRabbitMqService;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.entity.SysLoginLogVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.cms.common.constant.ConstantCommonCode.RABBITMQ_EXCHANGE_LOG;
import static com.cms.common.constant.ConstantCommonCode.RABBITMQ_TOPIC_LOG;

/**
 * @author ydf Created by 2022/1/25 13:58
 */
@Service
public class OlapRabbitMqServiceImpl implements OlapRabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendLoginLog(HttpServletRequest request, SecurityClaimsUser securityClaimsUser, boolean flag) {
        Map<String,Object> objectMap = new HashMap<>(2);
        String message =  securityClaimsUser.getUsername() + "在：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 点击了登录";
        SysLoginLogVo buildObject = SysLoginLogVo.builder()
                .loginIp("127.0.0.1")
                .loginUserName(securityClaimsUser.getUsername())
                .title(message)
                .operatingSystem("windows10")
                .status(1)
                .type(1)
                .browser("谷歌")
                .message(null)
                .build();
        objectMap.put("info",buildObject);
        // RabbitMQ消息推送
        rabbitTemplate.convertAndSend(RABBITMQ_EXCHANGE_LOG, RABBITMQ_TOPIC_LOG, objectMap);
    }
}
