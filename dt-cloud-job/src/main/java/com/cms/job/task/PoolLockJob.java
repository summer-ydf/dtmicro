package com.cms.job.task;

import com.cms.common.utils.SysCmsUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

/**
 * @author ydf Created by 2021/12/10 16:27
 */
public abstract class PoolLockJob implements Job {

    public abstract void execute();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 加入锁机制，必须保证JOB的有序执行
        LockerFactory.Locker locker = LockerFactory.getLocker(getClass().getName());
        if(!locker.tryLock()) {
            SysCmsUtils.log.warn("系统正在处理任务:"+getClass().getSimpleName());
            return;
        }
        try{
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            String taskId = jobDataMap.getString("taskId");
            SysCmsUtils.log.info("任务开始执行：{}",taskId);
            execute();
        }finally {
            locker.unlock();
        }
    }
}
