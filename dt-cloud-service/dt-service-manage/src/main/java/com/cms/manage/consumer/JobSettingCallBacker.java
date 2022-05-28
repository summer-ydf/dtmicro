package com.cms.manage.consumer;

import com.cms.common.core.builder.SettingModelDataCallBacker;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务监听处理类
 * @author DT辰白 Created by 2022/4/19 15:50
 */
@Slf4j
public class JobSettingCallBacker implements SettingModelDataCallBacker {

    @Override
    public void callback(Object oldValue, Object newValue) {
        log.info("新的值【{}】",newValue);
        log.info("旧的值【{}】",oldValue);
    }
}
