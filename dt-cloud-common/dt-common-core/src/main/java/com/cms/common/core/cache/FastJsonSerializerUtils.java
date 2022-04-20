package com.cms.common.core.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

/**
 * @author ydf Created by 2022/3/22 10:11
 */
public class FastJsonSerializerUtils {

    private static final ParserConfig defaultRedisConfig = new ParserConfig();

    public static byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        } else {
            try {
                return JSON.toJSONBytes(object, new SerializerFeature[]{SerializerFeature.WriteClassName});
            } catch (Exception var3) {
                throw new SerializationException("Could not serialize: " + var3.getMessage(), var3);
            }
        }
    }

    public static Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length != 0) {
            try {
                return JSON.parseObject(new String(bytes, IOUtils.UTF8), Object.class, defaultRedisConfig, new Feature[0]);
            } catch (Exception var3) {
                throw new SerializationException("Could not deserialize: " + var3.getMessage(), var3);
            }
        } else {
            return null;
        }
    }
    public static  <T> T deserialize(byte[] bytes,Class<T> cl) throws SerializationException {
        if (bytes != null && bytes.length != 0) {
            try {
                return JSON.parseObject(new String(bytes, IOUtils.UTF8), cl, defaultRedisConfig, new Feature[0]);
            } catch (Exception var3) {
                throw new SerializationException("Could not deserialize: " + var3.getMessage(), var3);
            }
        } else {
            return null;
        }
    }

    static {
        defaultRedisConfig.setAutoTypeSupport(true);
    }
}
