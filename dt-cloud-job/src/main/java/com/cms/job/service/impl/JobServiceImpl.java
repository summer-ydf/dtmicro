package com.cms.job.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.job.entity.JobInformationEntity;
import com.cms.job.entity.QuartzJobInfo;
import com.cms.job.mapper.JobMapper;
import com.cms.job.service.JobService;
import com.cms.job.utils.QuartzUtils;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DT
 * @date 2022/3/28 19:27
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, JobInformationEntity> implements JobService {

    @Autowired
    private QuartzUtils quartzUtils;

    private static final String DEFAULT_PACKAGE = "com.cms.job.task.bean.";

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
        try {
            taskInfo.setTaskId(job.getTaskId());
            Class<? extends Job> aClass = (Class<? extends Job>) Class.forName(DEFAULT_PACKAGE + job.getJobClass());
            taskInfo.setJobClass(aClass);
            taskInfo.setCronExpression(job.getCronExpression());
            taskInfo.setTaskName(job.getTaskName());
            taskInfo.setTaskGroupName(job.getTaskGroupName());
            // 检验是否存在相同的计划任务
            boolean exists = quartzUtils.checkExists(job.getTaskId());
            if (exists) {
                return ResultUtil.error("任务已经存在");
            }
            // 启动定时任务
            Boolean addScheduleJob = quartzUtils.addScheduleJob(taskInfo);
            if (addScheduleJob) {
                // 添加任务信息
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
}
