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
 * @date 2021/11/20 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log_operator")
@ApiModel(value="数据操作日志对象")
@EqualsAndHashCode(callSuper = true)
public class SysLogOperatorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5244924931370321033L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "操作业务类型")
    private String businessType;

    @ApiModelProperty(value = "方法名称")
    private String requestMethodName;

    @ApiModelProperty(value = "请求方式")
    private String requestMethodType;

    @ApiModelProperty(value = "操作账号")
    private String requestUserName;

    @ApiModelProperty(value = "请求URL")
    private String requestUrl;

    @ApiModelProperty(value = "请求IP地址")
    private String requestIp;

    @ApiModelProperty(value = "请求参数json")
    private String requestParam;

    @ApiModelProperty(value = "返回参数json")
    private String responseParam;

    @ApiModelProperty(value = "操作状态：1正常 2异常")
    private Integer status;

    @ApiModelProperty(value = "错误信息")
    private String errorInfo;
}
