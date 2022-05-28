package com.cms.common.tool.constant;

/**
 * 公共常量代码
 * @author DT辰白 Created by 2021/11/25 11:13
 */
public class ConstantCode {

    /**
     * EHCACHE 缓存常量
     */
    public static final String EHCACHE_CONFIG_KEY = "default_config";
    public static final String EHCACHE_CONFIG_NAME = "cache_settings";

    /**
     * 密码错误缓存常量
     */
    public static final Integer LOCK_MINUTE = 15;
    public static final Integer LOCK_TIME = 5;

    /**
     * 数字常量
     */
    public static final Integer INT_ZERO = 0;
    public static final String STR_ZERO = "0";
    public static final Long LONG_ZERO = 0L;
    public static final Integer INT_ONE = 1;
    public static final String STR_ONE = "1";
    public static final Long LONG_ONE = 1L;
    public static final Integer INT_TWO = 2;
    public static final String STR_TWO = "2";
    public static final Long LONG_TWO = 2L;
    public static final Integer INT_THREE = 3;
    public static final String STR_THREE = "3";
    public static final Long LONG_THREE = 3L;

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

    // redis key
    public static final String CMS_KEY_PREX = "cms:";
    // 登录锁定
    public static final String CACHE_LOGIN_LOCK = CMS_KEY_PREX + "auth:login:lock:";
    // 登录token
    public static final String CACHE_LOGIN_TOKEN = CMS_KEY_PREX + "auth:login:token:";
    // 登录验证码
    public static final String CACHE_CODE_KEY = CMS_KEY_PREX + "auth:login:code:";
    // 手机验证码
    public static final String PHONE_CODE_KEY = CMS_KEY_PREX + "auth:phone:code:";
    // 操作员消息推送公众号Token
    public static final String WECHAT_TOKEN_KEY = CMS_KEY_PREX + "wx:token:code:";
    public static final String QYWECHAT_TOKEN_KEY = CMS_KEY_PREX + "qywx:token:code:";

    // OAuth2客户端初始化常量
    public static final String OAUTH_CLIENT_ID = "cms";
    public static final String OAUTH_CLIENT_SECRET = "dt$pwd123";
    public static final String OAUTH_SCOPE = "web";
    public static final String OAUTH_GRANT_TYPE = "password,refresh_token";
    public static final String AccountNonExpired = "User account has expired";
    public static final String AccountNonLocked = "User account is locked";
    public static final String Enabled = "User is disabled";
    public static final String CredentialsNonExpired = "User credentials have expired";

    // GateWay
    public static final String GATEWAY_AUTHORIZATION = "Cms-Gateway-Authorization";

    // ============RabbitMQ相关配置======
    // 主题队列
    public static final String RABBITMQ_LOG_TOPIC_QUEUE = "log-topic-queue";
    // 主题交换机
    public static final String RABBITMQ_LOG_TOPIC_EXCHANGE = "log-topic-exchange";
    // 主题路由键
    public static final String RABBITMQ_LOG_TOPIC_ROUTING_KEY = "log.*";

    // 死信队列
    public static final String RABBITMQ_LOG_DEAD_LETTER_QUEUE = "log-dead-letter-queue";
    // 死信交换机
    public static final String RABBITMQ_LOG_DEAD_LETTER_EXCHANGE = "log-dead-letter-exchange";
    // 死信路由键
    public static final String RABBITMQ_LOG_DEAD_LETTER_ROUTING_KEY = "log--dead-letter-routing-key";


    // ============数据权限配置======
    // 全部数据权限
    public static final Long DATA_SCOPE_ALL = 1L;
    // 自定数据权限
    public static final Long DATA_SCOPE_CUSTOM = 2L;
    // 部门数据权限
    public static final Long DATA_SCOPE_DEPT = 3L;
    // 部门及以下数据权限
    public static final Long DATA_SCOPE_DEPT_AND_CHILD = 4L;
    // 仅本人数据权限
    public static final Long DATA_SCOPE_SELF = 5L;
    // 数据权限过滤关键字
    public static final String DATA_SCOPE = "dataScope";
}
