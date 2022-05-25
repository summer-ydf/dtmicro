package com.cms.provider.config.base;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.cms.provider.config.handler.MyMetaObjectHandler;
import org.springframework.context.annotation.Bean;

/**
 * mybatis-plus 插件配置
 * @author DT辰白 Created by 2022/2/14 10:06
 */
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 自动填充
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        MyMetaObjectHandler myMetaObjectHandler = new MyMetaObjectHandler();
        globalConfig.setMetaObjectHandler(myMetaObjectHandler);
        return globalConfig;
    }
}
