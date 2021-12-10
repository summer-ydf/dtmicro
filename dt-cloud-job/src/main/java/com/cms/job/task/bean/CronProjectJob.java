package com.cms.job.task.bean;

import com.cms.common.utils.SysCmsUtils;
import com.cms.job.task.PoolLockJob;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 任务实现类
 * @author ydf Created by 2021/12/10 16:27
 */
public class CronProjectJob extends PoolLockJob {

    @Override
    public void execute() {
        SysCmsUtils.log.info("定时查询订单状态：{}",DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }
}
