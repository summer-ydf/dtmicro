package com.cms.auth.config.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author DT辰白 Created by 2022/1/6 15:01
 */
@JsonSerialize(using = CmsOAuthExceptionJacksonSerializer.class)
public class CmsOAuth2Exception extends OAuth2Exception {

    private String oauth2ErrorCode;

    private int httpErrorCode;

    public CmsOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CmsOAuth2Exception(String msg) {
        super(msg);
    }

    public String getOauth2ErrorCode() {
        return oauth2ErrorCode;
    }

    public void setOauth2ErrorCode(String oauth2ErrorCode) {
        this.oauth2ErrorCode = oauth2ErrorCode;
    }

    @Override
    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public void setHttpErrorCode(int httpErrorCode) {
        this.httpErrorCode = httpErrorCode;
    }
}

