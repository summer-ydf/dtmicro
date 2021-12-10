package com.cms.job.jobbean;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ydf Created by 2021/12/10 16:27
 */
@Slf4j
public abstract class PoolLockJob implements Job {

    public abstract void execute();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 加入锁机制，必须保证JOB的有序执行
        LockerFactory.Locker locker = LockerFactory.getLocker(getClass().getName());
        if(!locker.tryLock()) {
            log.info("系统正在处理任务:"+getClass().getSimpleName());
            return;
        }
        try{
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            String taskId = jobDataMap.getString("taskId");
            log.info("CRON表达式任务执行：{}",taskId);
            execute();
        }finally {
            locker.unlock();
        }
    }
}
