package com.cms.auth.config.handler;

import com.cms.auth.service.OlapRabbitMqService;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.utils.SysCmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 * @author ydf Created by 2022/1/7 14:07
 */
public class TokenAuthenticationSuccessHandler implements OAuth2AuthenticationSuccessHandler {

    @Autowired
    private OlapRabbitMqService olapRabbitMqService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, OAuth2Authentication authentication) throws IOException, ServletException {
        SecurityClaimsUser securityClaimsUser = (SecurityClaimsUser) authentication.getPrincipal();
        olapRabbitMqService.sendLoginLog(request,securityClaimsUser,true);
        SysCmsUtils.log.info("登录成功推送日志信息->>>" + securityClaimsUser.getUsername());
    }
}
