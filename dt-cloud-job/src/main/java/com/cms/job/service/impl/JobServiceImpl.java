package com.cms.job.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.tool.result.ResultUtil;
import com.cms.job.entity.JobInformationEntity;
import com.cms.job.entity.QuartzJobInfo;
import com.cms.job.mapper.JobMapper;
import com.cms.job.service.JobService;
import com.cms.job.utils.QuartzUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DT辰白
 * @date 2022/3/28 19:27
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, JobInformationEntity> implements JobService {

    @Autowired
    private QuartzUtils quartzUtils;
    @Autowired
    private IdGeneratorConfig idGeneratorConfig;

    @Override
    public ResultUtil<IPage<JobInformationEntity>> pageSearch(SysSearchPage request) {
        Page<JobInformationEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<JobInformationEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<String> addScheduleJob(JobInformationEntity job) {
        QuartzJobInfo taskInfo = new QuartzJobInfo();
        taskInfo.setTaskId(String.valueOf(idGeneratorConfig.nextId(Object.class)));
        try {
            Class<?> aClass = Class.forName(job.getJobClass());
            taskInfo.setJobClass((Class<? extends Job>) aClass);
            taskInfo.setCronExpression(job.getCronExpression());
            taskInfo.setTaskName(job.getTaskName());
            taskInfo.setTaskGroupName(job.getTaskGroupName());
            if (StringUtil.isNotBlank(job.getParams())) {
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("data",job.getParams());
                taskInfo.setDataMap(jobDataMap);
            }
            // 启动定时任务
            Boolean addScheduleJob = quartzUtils.addScheduleJob(taskInfo);
            if (addScheduleJob) {
                // 添加任务信息
                job.setStatus(1);
                job.setTaskId(taskInfo.getTaskId());
                this.baseMapper.insert(job);
                return ResultUtil.success();
            }else {
                return ResultUtil.error();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return ResultUtil.error("任务实例化转换出错");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<String> updateScheduleJob(JobInformationEntity jobInformationEntity) {
        try {
            Boolean aBoolean = quartzUtils.updateScheduleJob(jobInformationEntity.getTaskId(), jobInformationEntity.getCronExpression());
            if (aBoolean) {
                this.baseMapper.updateById(jobInformationEntity);
                return ResultUtil.success();
            }else {
                return ResultUtil.error("任务不存在");
            }
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<String> deleteScheduleJob(String taskId) {
        try {
            quartzUtils.deleteScheduleJob(taskId);
            this.baseMapper.delete(new QueryWrapper<JobInformationEntity>().eq("task_id", taskId));
            return ResultUtil.success();
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<String> pauseScheduleJob(String taskId) {
        try {
            Boolean aBoolean = quartzUtils.pauseScheduleJob(taskId);
            if (aBoolean) {
                JobInformationEntity informationEntity = this.baseMapper.selectOne(new QueryWrapper<JobInformationEntity>().eq("task_id", taskId));
                informationEntity.setStatus(0);
                this.baseMapper.updateById(informationEntity);
                return ResultUtil.success();
            }else {
                return ResultUtil.error("任务不存在");
            }
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }

    @Override
    public ResultUtil<String> resumeScheduleJob(String taskId) {
        try {
            Boolean aBoolean = quartzUtils.resumeScheduleJob(taskId);
            if (aBoolean){
                JobInformationEntity informationEntity = this.baseMapper.selectOne(new QueryWrapper<JobInformationEntity>().eq("task_id", taskId));
                informationEntity.setStatus(1);
                this.baseMapper.updateById(informationEntity);
                return ResultUtil.success();
            }else {
                return ResultUtil.error("任务不存在");
            }
        } catch (SchedulerException e) {
            return ResultUtil.error();
        }
    }


}
