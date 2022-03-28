package com.cms.job.test;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang.StringUtils;

/**
 * @author ydf Created by 2022/3/28 17:09
 */
@CommonsLog
public class JobContext {
    public static void startSysJob(){
        //通知第三方业务
        //通知第三方业务
//        if(StringUtils.isNotBlank(SchoolPropertyUtils.getSchoolProperty("flow.wx.template.cron"))){
//            log.info("初始化消息推送定时任务......");
//            String cron=SchoolPropertyUtils.getSchoolProperty("flow.wx.template.cron");
//            ScheduleUtils.ScheduleTaskInfo taskInfo=new ScheduleUtils.ScheduleTaskInfo()
//                    .taskId(SendWxMessageJob.class.getName())
//                    .jobClass(SendWxMessageJob.class)
//                    .cronExpression(cron)
//                    .taskName("消息推送");
//            if(ScheduleUtils.checkExists(taskInfo.getTaskId())){
//                ScheduleUtils.deleteTask4Schedule(taskInfo.getTaskId());
//            }
//            ScheduleUtils.addTaskToSchedule(taskInfo);
//            log.info("初始化消息推送定时任务完成......");
//        }

    }
}
