package com.cms.auth.config.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author ydf Created by 2022/1/6 11:59
 */
public class JiheOauthExceptionJacksonSerializer extends StdSerializer<JiheOauth2Exception> {

    public JiheOauthExceptionJacksonSerializer() {
        super(JiheOauth2Exception.class);
    }

    @Override
    public void serialize(JiheOauth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        gen.writeStartObject();
        gen.writeStringField("errorCode", String.valueOf(value.getHttpErrorCode()));
        gen.writeStringField("message", value.getMessage());
        gen.writeStringField("path", request.getServletPath());
        gen.writeStringField("timestamp", String.valueOf(System.currentTimeMillis()));
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}

