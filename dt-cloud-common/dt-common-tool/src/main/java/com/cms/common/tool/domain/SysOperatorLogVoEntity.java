package com.cms.common.tool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/2/11 16:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperatorLogVoEntity implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 操作业务类型
     */
    private String businessType;

    /**
     * 方法名称
     */
    private String requestMethodName;

    /**
     * 请求方式
     */
    private String requestMethodType;

    /**
     * 操作账号
     */
    private String requestUserName;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求IP地址
     */
    private String requestIp;

    /**
     * 请求参数json
     */
    private String requestParam;

    /**
     * 返回参数json
     */
    private String responseParam;

    /**
     * 操作状态：1正常 2异常
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorInfo;
}
