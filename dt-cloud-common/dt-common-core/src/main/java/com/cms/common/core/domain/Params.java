package com.cms.common.core.domain;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ydf Created by 2022/4/19 16:05
 */
public class Params extends JSONObject {

    public Params() {
        super(new HashMap());
    }

    public Params(Map map) {
        super(map);
    }

    public Params(int initialCapacity) {
        super(initialCapacity);
    }

    public Params p(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getString(String key,String def) {
        return StringUtils.defaultIfBlank(getString(key),def);
    }
}
