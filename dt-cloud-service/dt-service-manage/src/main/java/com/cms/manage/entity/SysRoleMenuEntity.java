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
 * @author ydf Created by 2022/2/17 15:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
@ApiModel(value="角色权限关联对象")
public class SysRoleMenuEntity implements Serializable {

    private static final long serialVersionUID = 7545367204249802832L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "菜单权限id")
    private String menuId;
}
