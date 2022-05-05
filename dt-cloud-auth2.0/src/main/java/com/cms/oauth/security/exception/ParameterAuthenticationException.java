package com.cms.oauth.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 参数异常
 * @author ydf Created by 2022/4/28 10:29
 */
public class ParameterAuthenticationException extends AuthenticationException {

    public ParameterAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public ParameterAuthenticationException(String msg) {
        super(msg);
    }
}
