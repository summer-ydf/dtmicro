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

    SYSTEM_ERROR(50000, "系统执行错误"),
    OAuth2Exception(50001, "认证错误"),
    SERVICE_NOT_FOUND(50002, "服务标识不存在"),
    BUILD_API_REQUEST_ERROR(50003, "构建参数错误"),
    REQUEST_PARAMS_ERROR(50004, "请求参数不合法，缺少必要数据"),
    REQUEST_APPID_NOT_MATCH(50005, "APPID不合法"),
    REQUEST_OAUTH_EXP(50006, "授权已过期"),
    REQUEST_NOT_SCOPE(50007, "资源访问受限"),
    TEMPLATE_NOT_FOUNT(50008, "未找到资源"),
    INSIDE_PARAMS_ERROR(50009, "报文格式错误"),
    UPLOAD_FILE_ERROR(50010, "文件上传失败"),
    SIGN_ERROR(50011, "签名错误"),
    Resource_not_found(50012, "未找到该应用"),
    valid_code_error(50013, "验证码错误"),
    valid_password_error(50014, "用户名或密码错误"),
    RPC_ERROR(50015, "远程服务异常"),
    RPC_HYSTRIX_ERROR(50016, "远程服务响应超时"),
    SERVICE_ERROR(50017, "业务错误"),
    Getaway_timeout(50018, "网关超时"),
    Username_not_found(50019, "未找到用户信息");

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
