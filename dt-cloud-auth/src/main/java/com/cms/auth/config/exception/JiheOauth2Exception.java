package com.cms.auth.config.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author ydf Created by 2022/1/6 11:59
 */
@JsonSerialize(using = JiheOauthExceptionJacksonSerializer.class)
public class JiheOauth2Exception extends OAuth2Exception {

    public JiheOauth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public JiheOauth2Exception(String msg) {
        super(msg);
    }
}
