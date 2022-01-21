package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统：用户关联角色实体
 * @author DT
 * @date 2021/6/5 0:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_operator_role")
@ApiModel(value="操作员关联角色对象")
public class SysOperatorRoleEntity implements Serializable {

    private static final long serialVersionUID = 4184255325377155893L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
