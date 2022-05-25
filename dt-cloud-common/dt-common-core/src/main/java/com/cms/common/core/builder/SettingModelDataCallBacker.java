package com.cms.common.core.builder;

/**
 * 配置模型回调接口
 * @author DT辰白
 * @date 2022/4/17 14:43
 */
@FunctionalInterface
public interface SettingModelDataCallBacker {

    void callback(Object oldValue, Object newValue);

}
