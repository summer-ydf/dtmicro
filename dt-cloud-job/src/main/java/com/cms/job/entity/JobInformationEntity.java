package com.cms.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cms.common.jdbc.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author DT
 * @date 2022/3/28 19:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_job_information")
@ApiModel(value="部门机构对象")
@EqualsAndHashCode(callSuper = true)
public class JobInformationEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务组名称")
    private String taskGroupName;

    @ApiModelProperty(value = "具体的定时任务实现类")
    private String jobClass;

    @ApiModelProperty(value = "任务运行时间表达式")
    private String cronExpression;

    @ApiModelProperty(value = "执行状态")
    private Integer status;

    @ApiModelProperty(value = "参数")
    private String params;

}
