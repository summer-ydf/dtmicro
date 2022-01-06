package com.cms.common.result;

/**
 * 统一返回状态码
 * @author ydf Created by 2021/11/25 11:12
 */
public enum ResultEnum {

    /**
     * 统一状态返回
     */
    SUCCESS(2000),SUCCESS_MESSAGE("操作成功！"),
    ERROR(2001),ERROR_MESSAGE("操作失败！"),
    NO_LOGIN(4001),NO_LOGIN_MESSAGE("匿名用户无权限访问！"),
    NO_AUTH(4002),NO_AUTH_MESSAGE("无资源访问权限！"),
    ACCOUNT_OTHERS(4003),ACCOUNT_OTHERS_MESSAGE("账号下线！"),
    SYSTEM_ERROR(50000, "系统执行错误！"),
    OAuth2Exception(50001, "认证错误！"),
    OAUTH2_GRANTTYPE_ERROR(50003, "非密码模式！"),
    OAUTH2_BASE_ERROR(50003, "客户端认证失败！"),
    RESOURCE_OAUTH_EXP(50004, "服务端授权过期！"),
    VALID_CODE_ERROR(50005, "验证码错误！"),
    VALID_USERNAME_ERROR(50006, "用户名或者密码错误！");

    private Integer code;

    private String message;

    ResultEnum(Integer code){
        this.code = code;
    }

    ResultEnum(String message){
        this.message = message;
    }

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
