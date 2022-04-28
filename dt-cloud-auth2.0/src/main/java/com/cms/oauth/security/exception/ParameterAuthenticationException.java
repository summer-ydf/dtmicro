package com.cms.oauth.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 参数异常
 * @author DT
 * @date 2022/4/27 20:46
 */
public class ParameterAuthenticationException extends AuthenticationException {

    public ParameterAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public ParameterAuthenticationException(String msg) {
        super(msg);
    }
}
