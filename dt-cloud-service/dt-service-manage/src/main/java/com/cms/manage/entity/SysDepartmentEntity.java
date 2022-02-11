package com.cms.manage.entity;

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
 * 系统：部门机构实体
 * @author DT
 * @date 2021/6/13 11:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_department")
@ApiModel(value="部门机构对象")
@EqualsAndHashCode(callSuper = true)
public class SysDepartmentEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8511432947454336028L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "上级部门id")
    private String pid;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级部门名称")
    private String parentName;

    @ApiModelProperty(value = "序号")
    private Integer orderNum;
}
