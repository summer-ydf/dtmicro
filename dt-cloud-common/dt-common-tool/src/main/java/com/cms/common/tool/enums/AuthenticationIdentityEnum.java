package com.cms.common.tool.enums;

import lombok.Getter;

/**
 * 客户端登录身份标识
 * @author DT辰白 Created by 2022/4/29 13:04
 */
public enum AuthenticationIdentityEnum implements IBaseEnum<String> {

    USERNAME("username", "PC端登录"),
    MOBILE("mobile", "APP端登录"),
    IDCARD("idcard", "身份证号登录"),
    OPENID("openId", "小程序微信认证登录");

    @Getter
    private String value;

    @Getter
    private String label;

    AuthenticationIdentityEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
