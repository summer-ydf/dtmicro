package com.cms.job.jobbean;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 使用CRON表达式的任务执行器
 * @author ydf Created by 2021/6/17 10:54
 */
@Slf4j
@Component
public class CronProcessJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //加入锁机制，必须保证JOB的有序执行
        LockerFactory.Locker locker = LockerFactory.getLocker(getClass().getName());
        if(!locker.tryLock()){
            log.info("系统正在处理:查询支付订单状态任务");
            return;
        }
        try{
//            log.info("开始推送微信消息");
//            WorkflowService workflowService= SpringUtils.getBean(WorkflowService.class);
//            workflowService.send_message();
//            log.info("结束推送微信消息");

            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
//            String data = jobDataMap.getString("data");
            log.info("CRON表达式任务执行：{}",jobDataMap);


        }finally {
            locker.unlock();
        }

    }
}
