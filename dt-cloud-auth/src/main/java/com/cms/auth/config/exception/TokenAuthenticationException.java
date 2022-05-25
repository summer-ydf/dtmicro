package com.cms.auth.config.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author DT辰白 Created by 2022/1/6 18:06
 */
public class TokenAuthenticationException extends AuthenticationException {

    private int code;
    public TokenAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenAuthenticationException(String msg,int code) {
        super(msg);
        this.code=code;
    }
}
