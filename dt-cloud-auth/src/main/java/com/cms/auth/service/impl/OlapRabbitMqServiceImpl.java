package com.cms.auth.service.impl;

import com.cms.auth.service.OlapRabbitMqService;
import com.cms.common.core.utils.CoreWebUtils;
import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysLoginLogVoEntity;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.cms.common.tool.constant.ConstantCode.RABBITMQ_LOG_TOPIC_EXCHANGE;

/**
 * @author DT辰白 Created by 2022/1/25 13:58
 */
@Service
public class OlapRabbitMqServiceImpl implements OlapRabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IdGeneratorConfig idGeneratorConfig;

    @Override
    public void sendLoginLog(HttpServletRequest request, SecurityClaimsUserEntity securityClaimsUser, boolean flag) {
        String agent = request.getHeader("User-Agent");
        // 解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        // 获取浏览器对象
        Browser browser = userAgent.getBrowser();
        // 获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String message =  securityClaimsUser.getUsername() + "在：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 点击了登录";
        SysLoginLogVoEntity buildObject = SysLoginLogVoEntity.builder()
                .messageId(String.valueOf(idGeneratorConfig.nextId(Object.class)))
                .loginIp(CoreWebUtils.getIpAddress(request))
                .loginUserName(securityClaimsUser.getUsername())
                .title(message)
                .operatingSystem(operatingSystem.getName())
                .status(1)
                .type(flag ? 1 : 2)
                .browser(browser.getName())
                .message(null)
                .build();
        // RabbitMQ消息推送
        CorrelationData correlationData = new CorrelationData(buildObject.getMessageId());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(RABBITMQ_LOG_TOPIC_EXCHANGE, "log.dt",buildObject,correlationData);
    }
}
