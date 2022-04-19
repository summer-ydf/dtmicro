package com.cms.common.core.builder;

/**
 * @author DT
 * @date 2022/4/17 14:43
 */
@FunctionalInterface
public interface SettingModelDataCallBacker {
    void callback(Object oldValue, Object newValue);
}
