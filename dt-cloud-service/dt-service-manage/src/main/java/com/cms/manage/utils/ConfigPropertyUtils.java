package com.cms.manage.utils;

import com.cms.common.core.domain.Params;
import com.cms.manage.service.ConfigPropertyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext中的所有bean
 * @author DT辰白
 * @date 2022/5/21 19:23
 */
@Component("configPropertyUtils")
@Lazy(false)
public class ConfigPropertyUtils implements ApplicationContextAware {

    private static ConfigPropertyService configPropertyService;


    public static Params params() {
        return ConfigPropertyUtils.configPropertyService.configParams();
    }

    public static String getConfigProperty(String key) {
        return StringUtils.defaultIfBlank(ConfigPropertyUtils.configPropertyService.configParams().getString(key), "");
    }

    public static String getConfigProperty(String key, String defaultValue) {
        String val = getConfigProperty(key);
        return StringUtils.isEmpty(val) ? defaultValue : val;
    }

    public static String getConfigPropertyEnd(String key, String defaultValue,String last) {
        String val = getConfigProperty(key,defaultValue);
        if(StringUtils.isNotBlank(val)) {
            return val.endsWith(last) ? val : val+last;
        }
        return val;
    }

    public static String getConfigPropertyEnd(String key,String last) {
        String val = getConfigProperty(key);
        if(StringUtils.isNotBlank(val)) {
            return val.endsWith(last) ? val : val+last;
        }
        return val;
    }

    public static String getConfigPropertyStart(String key, String defaultValue,String start) {
        String val = getConfigProperty(key,defaultValue);
        if(StringUtils.isNotBlank(val)) {
            return val.startsWith(start) ? val : start+val;
        }
        return val;
    }

    public static String getConfigPropertyStartRemove(String key, String defaultValue,String start) {
        String val = getConfigProperty(key,defaultValue);
        if(StringUtils.isNotBlank(val)) {
            return val.startsWith(start) ? val.substring(1) : val;
        }
        return val;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigPropertyUtils.configPropertyService = applicationContext.getBean(ConfigPropertyService.class);
    }
}
