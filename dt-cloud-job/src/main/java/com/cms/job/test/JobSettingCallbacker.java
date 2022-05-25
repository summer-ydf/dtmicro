package com.cms.job.test;

import org.apache.commons.lang.ObjectUtils;

/**
 * @author DT辰白 Created by 2022/3/28 17:07
 */
public class JobSettingCallbacker implements SettingModelDataCallbacker{
    @Override
    public void callback(Object old_value, Object new_value) throws Exception {
        // 如果是系统设置的定时任务
        if(!ObjectUtils.toString(old_value).equalsIgnoreCase(ObjectUtils.toString(new_value))){
            // 任务时间发生改变的时候，立即执行新的调度方案
            JobContext.startSysJob();
        }
    }
}
