package com.cms.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.job.entity.JobInformationEntity;
import com.cms.job.entity.QuartzJobInfo;
import com.cms.job.service.JobService;
import com.cms.job.task.bean.CronProjectJob;
import com.cms.job.utils.QuartzUtils;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

/**
 * @author ydf Created by 2021/12/10 14:33
 */
@RestController
@RequestMapping("/job")
public class JobController {

    private final QuartzUtils quartzUtils;
    private final JobService jobService;

    public JobController(QuartzUtils quartzUtils, JobService jobService) {
        this.quartzUtils = quartzUtils;
        this.jobService = jobService;
    }

    @ApiOperation(value = "分页查询任务列表")
    @GetMapping("/page")
    public ResultUtil<IPage<JobInformationEntity>> page(SysSearchPage request) {
        return jobService.pageSearch(request);
    }

    @ApiOperation(value = "添加任务")
    @PostMapping("/addScheduleJob")
    public ResultUtil<String> addScheduleJob(@RequestBody JobInformationEntity jobInformationEntity) {
       return jobService.addScheduleJob(jobInformationEntity);
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
