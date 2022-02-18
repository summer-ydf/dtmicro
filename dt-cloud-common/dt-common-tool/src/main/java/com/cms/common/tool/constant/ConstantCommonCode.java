package com.cms.common.tool.constant;

/**
 * 公共常量代码
 * @author ydf Created by 2021/11/25 11:13
 */
public class ConstantCommonCode {

    /**
     * 资源白名单
     */
    public static final String LOGIN_URL = "/api/user/login";
    public static final String INIT_URL = "/api/v1/config/init";
    public static final String API = "/api";
    public static final  String CAPTCHA = "/api/v1/verification/getCode";
    public static final String SWAGGER_DOC = "/doc.html";
    public static final String SWAGGER_SFX = "/webjars";
    public static final String SWAGGER_RES = "/swagger-resources";
    public static final String SWAGGER_API = "/v2/api-docs";
    public static final String DRUID_PATH = "/druid";
    public static final String WEBSOCKET_PATH = "/api/ws";

    /**
     * 系统缓存常量
     */
    public static final String USER_KEY = "dt::user::";
    public static final String PERMISSION_KEY = "dt::permission::";
    public static final Long REDIS_EXP_TIME = 60 * 60L;
    public static final Long KAPTCHA_EXP_TIME = 60 * 3L;

    /**
     * 密码错误缓存常量
     */
    public static final String LOGIN_LOCK = "dt::login::";
    public static final Integer LOCK_MINUTE = 15;
    public static final Integer LOCK_TIME = 5;

    /**
     * 数字常量
     */
    public static final Integer INT_ONE = 1;
    public static final String STR_ONE = "1";
    public static final Long LONG_ONE = 1L;
    public static final Integer INT_TWO = 2;
    public static final String STR_TWO = "2";
    public static final Long LONG_TWO = 2L;

    public static final String SYS_USER_TABLE_NAME = "CMS用户信息表V1.0";
    public static final String SYS_USER_SHEET_NAME = "CMS系统用户信息";

    /**
     * 验证码宽高常量
     */
    public static final Integer WIDTH = 180;
    public static final Integer HEIGHT = 45;

    /**
     * 图片后缀名称
     */
    public static final String IMG_JPG = "jpg";
    public static final String IMG_PNG = "png";
    public static final String IMG_JPEG = "jpeg";


    /**
     * 系统参数配置常量
     */
    public static final String BASE_CONFIG_THEME_TYPE = "系统主题配置";
    public static final String BASE_CONFIG_INDEX_TYPE = "首页桌面配置";
    public static final String BASE_CONFIG_MSG_TYPE = "消息推送配置";
    public static final String BASE_CONFIG_LOGIN_TYPE = "系统登录配置";
    public static final String BASE_CONFIG_THEME_K_ONE = "主题一";
    public static final String BASE_CONFIG_THEME_V_ONE = "theme_one.css";
    public static final String BASE_CONFIG_THEME_K_TWO = "主题二";
    public static final String BASE_CONFIG_THEME_V_TWO = "theme_two.css";
    public static final String BASE_CONFIG_THEME_K_THREE = "主题三";
    public static final String BASE_CONFIG_THEME_V_THREE = "theme_three.css";
    public static final String BASE_CONFIG_THEME_K_FOUR = "主题四";
    public static final String BASE_CONFIG_THEME_V_FOUR = "theme_four.css";
    public static final String BASE_CONFIG_THEME_K_FIVE = "主题五";
    public static final String BASE_CONFIG_THEME_V_FIVE = "theme_five.css";
    public static final String BASE_CONFIG_THEME_K_SIX = "主题六";
    public static final String BASE_CONFIG_THEME_V_SIX = "theme_six.css";

    /**
     * 文件存储桶
     */
    public static String MINIO_COMMON_BUCKET_NAME = "cms-bucket";

    /**
     * 登录日志标题常量
     */
    public static String LOGIN_STRING_KEY = "login";
    public static String LOGIN_STRING_TITLE = "登录系统";
    public static String LOGIN_MESSAGE_SUCCESS = "登录成功";
    public static String LOGINLOCK_STRING_KEY = "lock";
    public static String LOGINLOCK_STRING_TITLE = "账号被锁";
    public static String LOGINLOCK_MESSAGE_ERROR = "账号被锁定15分钟";
    public static String SIGNOUT_STRING_KEY = "signout";
    public static String SIGNOUT_STRING_TITLE = "退出系统";
    public static String SIGNOUT_MESSAGE_SUCCESS = "退出成功";
    public static String LOGIN_STRING_OS = "移动终端";

    // redis key
    public static final String CMS_KEY_PREX = "cms:";
    // 登录锁定
    public static final String CACHE_LOGIN_LOCK = CMS_KEY_PREX + "auth:login:lock:";
    // 登录token
    public static final String CACHE_LOGIN_TOKEN = CMS_KEY_PREX + "auth:login:token:";
    // 登录验证码
    public static final String CACHE_CODE_KEY = CMS_KEY_PREX + "auth:login:code:";

    // OAuth2客户端初始化常量
    public static final String OAUTH_CLIENT_ID = "cms";
    public static final String OAUTH_CLIENT_SECRET = "dt$pwd123";
    public static final String OAUTH_SCOPE = "web";
    public static final String OAUTH_GRANT_TYPE = "password,refresh_token";
    public static final String AccountNonExpired = "User account has expired";
    public static final String AccountNonLocked = "User account is locked";
    public static final String Enabled = "User is disabled";
    public static final String CredentialsNonExpired = "User credentials have expired";

    // 加密16位秘钥
    public static final String TOKEN_CLAIMS_PWD = "hJ6(vD2{hP1#fM2&";
    public static final String TOKEN_CLAIMS_IVS = "iC2!qD3#eX1;dT0&";
    public static final String GATEWAY_AUTHORIZATION = "Cms-Gateway-Authorization";

    // RabbitMQ相关配置
    public static final String RABBITMQ_TOPIC_LOG = "topic.cms.log";
    public static final String RABBITMQ_EXCHANGE_LOG = "CmsLogTopicExchange";
}
