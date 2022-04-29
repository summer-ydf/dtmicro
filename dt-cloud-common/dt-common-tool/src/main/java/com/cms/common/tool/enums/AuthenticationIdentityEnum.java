package com.cms.common.tool.enums;

import lombok.Getter;

/**
 * 客户端登录身份标识
 * @author ydf Created by 2022/4/29 13:04
 */
public enum AuthenticationIdentityEnum implements IBaseEnum<String> {

    USERNAME("username", "用户名登录"),
    MOBILE("mobile", "手机号登录"),
    IDCARD("idcard", "身份证号登录"),
    OPENID("openId", "微信认证登录");

    @Getter
    private String value;

    @Getter
    private String label;

    AuthenticationIdentityEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
