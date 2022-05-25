package com.cms.common.core.cache;

import org.springframework.core.NestedRuntimeException;

/**
 * @author DT辰白
 * @date 2022/4/20 20:26
 */
public class SerializationException extends NestedRuntimeException {
    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
