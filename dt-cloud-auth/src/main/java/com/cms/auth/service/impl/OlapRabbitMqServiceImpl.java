package com.cms.auth.service.impl;

import com.cms.auth.service.OlapRabbitMqService;
import com.cms.auth.utils.CoreWebUtils;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysLoginLogVo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.cms.common.tool.constant.ConstantCommonCode.RABBITMQ_EXCHANGE_LOG;
import static com.cms.common.tool.constant.ConstantCommonCode.RABBITMQ_TOPIC_LOG;

/**
 * @author ydf Created by 2022/1/25 13:58
 */
@Service
public class OlapRabbitMqServiceImpl implements OlapRabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendLoginLog(HttpServletRequest request, SecurityClaimsUserEntity securityClaimsUser, boolean flag) {
        Map<String,Object> objectMap = new HashMap<>(2);
        String agent = request.getHeader("User-Agent");
        // 解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        // 获取浏览器对象
        Browser browser = userAgent.getBrowser();
        // 获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String message =  securityClaimsUser.getUsername() + "在：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 点击了登录";
        SysLoginLogVo buildObject = SysLoginLogVo.builder()
                .loginIp(CoreWebUtils.getIpAddress(request))
                .loginUserName(securityClaimsUser.getUsername())
                .title(message)
                .operatingSystem(operatingSystem.getName())
                .status(1)
                .type(flag ? 1 : 2)
                .browser(browser.getName())
                .message(null)
                .build();
        objectMap.put("data",buildObject);
        // RabbitMQ消息推送
        rabbitTemplate.convertAndSend(RABBITMQ_EXCHANGE_LOG, RABBITMQ_TOPIC_LOG, objectMap);
    }
}
