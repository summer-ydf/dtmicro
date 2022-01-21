package com.cms.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 全局UUID工具类
 * @author ydf Created by 2021/11/25 11:22
 */
public class SysUuidUtils {

    /**
     * 生成的UUID是带-的字符串
     * @return 返回UUID
     */
    public static String randomUUID() {
        return IdUtil.randomUUID();
    }

    /**
     * 生成的UUID是不带-的字符串
     * @return 返回UUID
     */
    public static String simpleUUID() {
        return IdUtil.simpleUUID();
    }

    /**
     * 生成类似：5b9e306a4df4f8c54a39fb0c
     * @return 返回UUID
     */
    public static String objectUUID() {
        return IdUtil.objectId();
    }

    /**
     * 分布式唯一ID
     * @return 返回ID
     */
    public static Long nextId() {
        Snowflake snowflake = IdUtil.createSnowflake(1,1);
        return snowflake.nextId();
    }

}
