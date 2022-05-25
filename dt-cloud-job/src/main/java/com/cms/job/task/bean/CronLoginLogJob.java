package com.cms.job.task.bean;

import com.api.manage.feign.LogFeignClientService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.job.task.PoolLockJob;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 任务实现类
 * @author DT辰白 Created by 2021/12/10 16:27
 */
public class CronLoginLogJob extends PoolLockJob {

    @Autowired
    private LogFeignClientService logFeignClientService;

    @Override
    public void execute() {
        SysCmsUtils.log.info("定时查询登录日志记录数：{}",DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        ResultUtil<Long> resultUtil = logFeignClientService.findLoginLogCount();
        if (resultUtil.isSuccess()) {
            SysCmsUtils.log.info("查询到登录日志记录数总共有："+resultUtil.getData() + "条");
        }
    }
}
