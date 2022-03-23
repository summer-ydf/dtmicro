package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/3/23 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_dept")
@ApiModel(value="角色数据权限关联对象")
public class SysRoleDeptEntity implements Serializable {

    private static final long serialVersionUID = 7545367204249802832L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "部门id")
    private Long deptId;
}
