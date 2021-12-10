package com.cms.job.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ydf Created by 2021/12/10 15:13
 */
@Data
public class QuartzJobEntity implements Serializable {

    private static final long serialVersionUID = -5654653895404075233L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名称
     */
    private String jobGroupName;

    /**
     * 具体的定时任务实现类
     */
    private String jobBeanClass;

    /**
     * 任务运行时间表达式
     */
    private String jibCron;

    /**
     * 任务状态（1：启动 0：关闭）
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;
}
