package com.cms.auth.config.exception;

import com.cms.auth.config.handler.RestExceptionHandler;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 自定义异常序列化处理
 * @author ydf Created by 2022/1/6 15:01
 */
public class CmsOAuthExceptionJacksonSerializer extends StdSerializer<CmsOAuth2Exception> {

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    protected CmsOAuthExceptionJacksonSerializer() {
        super(CmsOAuth2Exception.class);
    }

    @Override
    public void serialize(CmsOAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        restExceptionHandler.loginExceptionHandler(jgen,value);
    }
}
