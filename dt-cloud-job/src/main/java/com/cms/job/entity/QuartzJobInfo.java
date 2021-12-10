package com.cms.job.entity;

import lombok.Data;
import org.quartz.Job;
import org.quartz.JobDataMap;

import java.io.Serializable;

/**
 * @author ydf Created by 2021/12/10 15:13
 */
@Data
public class QuartzJobInfo implements Serializable {

    private static final long serialVersionUID = -5654653895404075233L;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务组名称
     */
    private String taskGroupName;

    /**
     * 具体的定时任务实现类
     */
    private Class<? extends Job> jobClass;

    /**
     * 任务运行时间表达式
     */
    private String cronExpression;

    /**
     * 任务状态（1：启动 0：关闭）
     */
    private Integer status;

    /**
     * 存储数据
     */
    private JobDataMap dataMap;
}
