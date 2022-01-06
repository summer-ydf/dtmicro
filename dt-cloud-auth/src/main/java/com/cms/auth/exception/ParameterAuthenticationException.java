package com.cms.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ydf Created by 2022/1/6 14:11
 */
public class ParameterAuthenticationException extends AuthenticationException {

    private int code;
    public ParameterAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public ParameterAuthenticationException(String msg) {
        super(msg);
    }
}
