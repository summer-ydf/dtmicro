package com.cms.job.controller;

import com.cms.common.result.ResultUtil;
import com.cms.job.jobbean.CronProcessJob;
import com.cms.job.utils.QuartzUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/10 14:33
 */
@RestController
@RequestMapping("api/v1/job")
public class JobController {

    @Autowired
    private QuartzUtils quartzUtils;

    @PostMapping("/scheduleJob")
    public ResultUtil<String> scheduleJob(@RequestParam String jobName, @RequestParam String jobCron,
                                          @RequestParam String jobGroupName, @RequestParam String data) {
        quartzUtils.addScheduleJob(CronProcessJob.class, jobName, jobCron,jobGroupName, data);
        return ResultUtil.success(jobName);
    }

    @PostMapping("/deleteJob")
    public ResultUtil<String> deleteJob(@RequestParam String jobName, @RequestParam String jobGroupName) {
        try {
            quartzUtils.deleteScheduleJob(jobName, jobGroupName);
            return ResultUtil.success("删除成功");
        } catch (SchedulerException e) {
            return ResultUtil.success("删除失败");
        }
    }

    @PostMapping("/pauseScheduleJob")
    public ResultUtil<String> pauseScheduleJob(@RequestParam String jobName, @RequestParam String jobGroupName) {
        try {
            Boolean aBoolean = quartzUtils.pauseScheduleJob(jobName, jobGroupName);
            return ResultUtil.success(aBoolean ? "已暂停" : "任务不存在");
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }

    @PostMapping("/resumeScheduleJob")
    public ResultUtil<String> resumeScheduleJob(@RequestParam String jobName, @RequestParam String jobGroupName) {
        try {
            Boolean aBoolean = quartzUtils.resumeScheduleJob(jobName, jobGroupName);
            return ResultUtil.success(aBoolean ? "已恢复" : "任务不存在");
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }
}
