package com.cms.auth.service;


import com.cms.common.tool.domain.SecurityClaimsUserEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ydf Created by 2022/1/25 13:58
 */
public interface OlapRabbitMqService {

    void sendLoginLog(HttpServletRequest request, SecurityClaimsUserEntity securityClaimsUser, boolean flag);
}
