package com.cms.job.jobbean;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 任务实现类
 * @author ydf Created by 2021/12/10 16:27
 */
@Slf4j
public class CronProjectJob extends PoolLockJob {

    @Override
    public void execute() {
        System.out.println("查询数据库->>>"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }
}
