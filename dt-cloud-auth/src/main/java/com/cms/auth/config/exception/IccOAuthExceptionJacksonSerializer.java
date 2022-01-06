package com.cms.auth.config.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author ydf Created by 2022/1/6 15:01
 */
public class IccOAuthExceptionJacksonSerializer extends StdSerializer<IccOAuth2Exception> {
    @Autowired
    private RestExceptionHandler restExceptionHandler;
    protected IccOAuthExceptionJacksonSerializer() {
        super(IccOAuth2Exception.class);
    }

    @Override
    public void serialize(IccOAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        restExceptionHandler.loginExceptionHandler(jgen,value);
    }
}
