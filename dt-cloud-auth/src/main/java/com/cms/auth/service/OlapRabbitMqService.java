package com.cms.auth.service;

import com.cms.common.entity.SecurityClaimsUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ydf Created by 2022/1/25 13:58
 */
public interface OlapRabbitMqService {

    void sendLoginLog(HttpServletRequest request, SecurityClaimsUser securityClaimsUser, boolean flag);
}
