package com.cms.common.tool.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author DT
 * @date 2021/11/20 21:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLoginLogVo implements Serializable {

    private static final long serialVersionUID = -8336891240208854600L;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 登录账号
     */
    private String loginUserName;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String operatingSystem;

    /**
     * 登录状态：1成功 2失败
     */
    private Integer status;

    /**
     * 类型：1登录系统 2退出系统
     */
    private Integer type;

    /**
     * 操作信息
     */
    private String message;
}
