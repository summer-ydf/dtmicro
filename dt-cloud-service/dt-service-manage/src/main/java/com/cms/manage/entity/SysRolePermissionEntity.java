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
 * 系统：角色关联权限信息实体
 * @author DT
 * @date 2021/6/5 0:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_permission")
@ApiModel(value="角色权限关联对象")
public class SysRolePermissionEntity implements Serializable {

    private static final long serialVersionUID = 7545367204249802832L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    private Long permissionId;
}
