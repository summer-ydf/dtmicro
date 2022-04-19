package com.cms.manage.consumer;

import com.cms.common.core.builder.SettingModelDataCallBacker;

/**
 * @author ydf Created by 2022/4/19 15:50
 */
public class JobSettingCallBacker implements SettingModelDataCallBacker {

    @Override
    public void callback(Object oldValue, Object newValue) {
        System.out.println("新的值："+newValue);
        System.out.println("旧的值："+oldValue);
    }
}
