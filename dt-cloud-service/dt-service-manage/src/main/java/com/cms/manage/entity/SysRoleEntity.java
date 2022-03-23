package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
import java.util.Date;
import java.util.List;

/**
 * 系统：角色实体
 * @author DT
 * @date 2021/6/5 0:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="角色对象")
public class SysRoleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9168028949756773517L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色数据权限")
    private Long dataScope;

    @ApiModelProperty(value = "角色别名")
    private String alias;

    @ApiModelProperty(value = "角色备注")
    private String remark;

    @ApiModelProperty(value = "查询开始时间")
    @TableField(exist = false)
    private Date startTime;

    @ApiModelProperty(value = "查询结束时间")
    @TableField(exist = false)
    private Date endTime;

    @ApiModelProperty(value = "权限集合")
    @TableField(exist = false)
    private List<SysMenuEntity> children;

    @ApiModelProperty(value = "返回前端：部门ID")
    @TableField(exist = false)
    private List<Long> deptIds;
}
