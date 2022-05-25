package com.cms.common.tool.constant;

/**
 * @author DT辰白 Created by 2022/4/29 10:20
 */
public interface ConstantSecurityCode {

    /**
     * 认证请求头key
     */
    String AUTHORIZATION_KEY = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_PREFIX = "Bearer ";


    /**
     * Basic认证前缀
     */
    String BASIC_PREFIX = "Basic ";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT ID 唯一标识
     */
    String JWT_JTI = "jti";

    /**
     * JWT ID 唯一标识
     */
    String JWT_EXP = "exp";

    /**
     * 黑名单token前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    String USER_ID_KEY = "userId";

    String USER_NAME_KEY = "username";

    String CLIENT_ID_KEY = "client_id";

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";

    String GRANT_TYPE_KEY = "grant_type";

    String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * 认证身份标识
     */
    String AUTHENTICATION_IDENTITY_KEY = "authenticationIdentity";

    String APP_API_PATTERN = "/*/app-api/**";

    String LOGOUT_PATH = "/youlai-auth/oauth/logout";

    /**
     * 新增菜单路径,新增不存在的路由会导致系统无法访问，线上禁止新增菜单的操作
     */
    String SAVE_MENU_PATH = "/youlai-admin/api/v1/menus";

    /**
     * 验证码key前缀
     */
    String VALIDATE_CODE_PREFIX = "VALIDATE_CODE:";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_PREFIX = "SMS_CODE:";

    /**
     * PC端客户端ID
     */
    String WEB_CLIENT_ID = "cms-web";

    /**
     * PC端客户端ID (带验证码)
     */
    String ADMIN_CLIENT_ID = "cms-admin-web";

    /**
     * APP客户端ID
     */
    String APP_CLIENT_ID = "cms-app";

    /**
     * 小程序端客户端ID
     */
    String WECHAT_CLIENT_ID = "cms-wechat";

    /**
     * 自定义身份证号认证端客户端ID
     */
    String IDCARD_CLIENT_ID = "cms-idcard";
}
