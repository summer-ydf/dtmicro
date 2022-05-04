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
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 系统：操作员实体
 * @author DT
 * @date 2021/6/2 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_operator")
@ApiModel(value="操作员对象")
public class SysOperatorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3660353661543747205L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "身份证号码")
    private String idno;

    @ApiModelProperty(value = "微信号ID")
    private String openid;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "所属部门ID")
    private Long deptId;

    @ApiModelProperty(value = "是否超级管理员")
    private boolean isAdmin;

    @ApiModelProperty(value = "帐户是否过期(1 未过期，0已过期)")
    private boolean isAccountNonExpired = true;

    @ApiModelProperty(value = "帐户是否被锁定(1 未锁定，0已锁定)")
    private boolean isAccountNonLocked = true;

    @ApiModelProperty(value = "密码是否过期(1 未过期，0已过期)")
    private boolean isCredentialsNonExpired = true;

    @ApiModelProperty(value = "帐户是否可用(1 可用，0 删除用户)")
    private boolean isEnabled = true;

    @ApiModelProperty(value = "SQL变量：角色ID")
    @TableField(exist = false)
    private String strRoleIds;

    @ApiModelProperty(value = "SQL变量：部门名称")
    @TableField(exist = false)
    private String deptName;

    @ApiModelProperty(value = "返回前端：角色ID")
    @TableField(exist = false)
    private List<Long> roleIds;

    @ApiModelProperty(value = "返回前端：角色名称")
    @TableField(exist = false)
    private List<String> roleNames;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysOperatorEntity user = (SysOperatorEntity) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}
