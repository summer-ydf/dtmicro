package com.cms.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.job.entity.JobInformationEntity;
import com.cms.job.service.JobService;
import com.cms.job.utils.QuartzUtils;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "修改任务")
    @PostMapping("/updateScheduleJob")
    public ResultUtil<String> updateScheduleJob(@RequestBody JobInformationEntity jobInformationEntity) {
        return jobService.updateScheduleJob(jobInformationEntity);
    }

    @ApiOperation(value = "删除任务")
    @DeleteMapping("/deleteScheduleJob/{taskId}")
    public ResultUtil<String> deleteScheduleJob(@PathVariable String taskId) {
        return jobService.deleteScheduleJob(taskId);
    }

    @ApiOperation(value = "暂停任务")
    @PostMapping("/pauseScheduleJob/{taskId}")
    public ResultUtil<String> pauseScheduleJob(@PathVariable String taskId) {
        return jobService.pauseScheduleJob(taskId);
    }

    @ApiOperation(value = "恢复任务")
    @PostMapping("/resumeScheduleJob/{taskId}")
    public ResultUtil<String> resumeScheduleJob(@PathVariable String taskId) {
        return jobService.resumeScheduleJob(taskId);
    }

    @ApiOperation(value = "查询任务是否存在")
    @PostMapping("/checkExistsJob")
    public ResultUtil<String> checkExists(@RequestParam String taskId) {
        boolean aBoolean = quartzUtils.checkExists(taskId);
        return ResultUtil.success(aBoolean ? "任务已经存在" : "任务不存在");
    }
}
