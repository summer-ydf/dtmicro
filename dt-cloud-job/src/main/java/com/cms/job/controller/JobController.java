package com.cms.job.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.job.entity.QuartzJobInfo;
import com.cms.job.task.bean.CronProjectJob;
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

    @PostMapping("/addScheduleJob")
    public ResultUtil<String> addScheduleJob(@RequestParam String taskId, @RequestParam String jobName, @RequestParam String jobCron,
                                             @RequestParam String jobGroupName) {
        QuartzJobInfo taskInfo = new QuartzJobInfo();
        taskInfo.setTaskId(taskId);
        taskInfo.setJobClass(CronProjectJob.class);
        taskInfo.setCronExpression(jobCron);
        taskInfo.setTaskName(jobName);
        taskInfo.setTaskGroupName(jobGroupName);
        // 启动定时任务
        Boolean aBoolean = quartzUtils.addScheduleJob(taskInfo);
        return ResultUtil.success(aBoolean ? "任务开启" : "任务不存在");
    }

    @PostMapping("/updateScheduleJob")
    public ResultUtil<String> updateScheduleJob(@RequestParam String taskId, @RequestParam String jobCron) {
        try {
            Boolean aBoolean = quartzUtils.updateScheduleJob(taskId, jobCron);
            return ResultUtil.success(aBoolean ? "任务已修改" : "任务不存在");
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }

    @PostMapping("/deleteScheduleJob")
    public ResultUtil<String> deleteScheduleJob(@RequestParam String taskId) {
        try {
            quartzUtils.deleteScheduleJob(taskId);
            return ResultUtil.success("删除成功");
        } catch (SchedulerException e) {
            return ResultUtil.success("删除失败");
        }
    }

    @PostMapping("/pauseScheduleJob")
    public ResultUtil<String> pauseScheduleJob(@RequestParam String taskId) {
        try {
            Boolean aBoolean = quartzUtils.pauseScheduleJob(taskId);
            return ResultUtil.success(aBoolean ? "任务已暂停" : "任务不存在");
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }

    @PostMapping("/resumeScheduleJob")
    public ResultUtil<String> resumeScheduleJob(@RequestParam String taskId) {
        try {
            Boolean aBoolean = quartzUtils.resumeScheduleJob(taskId);
            return ResultUtil.success(aBoolean ? "任务已恢复" : "任务不存在");
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }

    @PostMapping("/checkExistsJob")
    public ResultUtil<String> checkExists(@RequestParam String taskId) {
        boolean aBoolean = quartzUtils.checkExists(taskId);
        return ResultUtil.success(aBoolean ? "任务已经存在" : "任务不存在");
    }
}
