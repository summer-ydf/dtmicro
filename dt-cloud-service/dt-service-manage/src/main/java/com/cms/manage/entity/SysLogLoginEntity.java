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
 * @author DT
 * @date 2021/11/20 21:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log_login")
@ApiModel(value="登录日志对象")
@EqualsAndHashCode(callSuper = true)
public class SysLogLoginEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8336891240208854600L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "日志标题")
    private String title;

    @ApiModelProperty(value = "登录账号")
    private String loginUserName;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @ApiModelProperty(value = "登录浏览器")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;

    @ApiModelProperty(value = "登录状态：1成功 2失败")
    private Integer status;

    @ApiModelProperty(value = "类型：1登录系统 2退出系统")
    private Integer type;

    @ApiModelProperty(value = "操作信息")
    private String message;
}
