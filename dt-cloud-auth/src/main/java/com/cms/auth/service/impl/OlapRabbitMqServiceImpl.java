package com.cms.auth.service.impl;

import com.cms.auth.service.OlapRabbitMqService;
import com.cms.auth.utils.CoreWebUtils;
import com.cms.common.entity.SecurityClaimsUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ydf Created by 2022/1/25 13:58
 */
@Service
public class OlapRabbitMqServiceImpl implements OlapRabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendLoginLog(HttpServletRequest request, SecurityClaimsUser securityClaimsUser, boolean flag) {
        Map<String,Object> map = new HashMap<>(6);
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        map.put("data",securityClaimsUser);
        map.put("time",createTime);
        map.put("flag",flag);
        map.put("ip", CoreWebUtils.getIpAddress(request));
        // 将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
    }
}
