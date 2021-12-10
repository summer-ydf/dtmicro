package com.cms.job.utils;

import cn.hutool.core.lang.UUID;
import com.alibaba.nacos.common.utils.LoggerUtils;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

/**
 * @author ydf Created by 2021/12/10 14:21
 */
@Slf4j
@Component
public class QuartzUtils {

    private final Scheduler scheduler;

    public QuartzUtils(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 创建JOB
     * @param jobBeanClass 具体的定时任务实现类
     * @param jobName 任务名称
     * @param jobCron 时间表达式 （如：0/5 * * * * ? ）
     * @param jobGroupName 任务组名称
     * @param data 自定义参数
     */
    public void addScheduleJob(Class<? extends Job> jobBeanClass, String jobName, String jobCron, String jobGroupName, String data) {
        // 创建需要执行的任务
        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                .withIdentity(jobName, jobGroupName)
                .usingJobData("data", data)
                .build();
        // 创建触发器，指定任务执行时间
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(jobCron))
                .build();
        // 把作业和触发器注册到任务调度中
        try {
            this.scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.info("创建任务异常：{}",e.toString());
        }
    }

    /**
     * 删除任务
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     */
    public void deleteScheduleJob(String jobName, String jobGroupName) throws SchedulerException {
        // 暂停触发器
        scheduler.pauseTrigger(new TriggerKey(jobName, jobGroupName));
        // 移除触发器中的任务
        scheduler.unscheduleJob(new TriggerKey(jobName, jobGroupName));
        // 删除任务
        scheduler.deleteJob(new JobKey(jobName, jobGroupName));
    }

    /**
     * 暂停某个任务
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     * @return 返回
     */
    public Boolean pauseScheduleJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return false;
        } else {
            scheduler.pauseJob(jobKey);
            return true;
        }
    }

    /**
     * 恢复某个任务
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     * @return 返回
     */
    public Boolean resumeScheduleJob(String jobName, String jobGroupName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return false;
        } else {
            scheduler.resumeJob(jobKey);
            return true;
        }
    }

    /**
     * 修改任务
     * @param jobName 任务名称
     * @param jobCron 时间表达式 （如：0/5 * * * * ? ）
     * @param jobGroupName 任务组名称
     * @return 返回
     */
    public Boolean updateScheduleJob(String jobName, String jobCron, String jobGroupName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 表达式调度构建器,不立即执行
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobCron).withMisfireHandlingInstructionDoNothing();
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            return true;
        } else {
            return false;
        }

    }

}
