package com.cms.auth.config.feign;

import com.cms.auth.config.interceptor.OkHttpLogInterceptor;
import feign.Feign;
import okhttp3.ConnectionPool;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/****
 * 需要修改成OKHTTP的客户端，需要在配置文件增加
 * feign.httpclient.enabled=false
 * feign.okhttp.enabled=true
 * @author ydf Created by 2022/4/21 17:44
 */
@AutoConfigureBefore(FeignAutoConfiguration.class)
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignOkHttpConfig {

    private int feignOkHttpReadTimeout = 60;
    private int feignConnectTimeout = 60;
    private int feignWriteTimeout = 120;

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                .readTimeout(feignOkHttpReadTimeout, TimeUnit.SECONDS)
                .connectTimeout(feignConnectTimeout, TimeUnit.SECONDS)
                .writeTimeout(feignWriteTimeout, TimeUnit.SECONDS)
                // 最大保持连接数为 10 ，并且在 5 分钟不活动之后被清除
                .connectionPool(new ConnectionPool(10 , 5L, TimeUnit.MINUTES)) // 配置连接池，也可以自定义
                .addInterceptor(new OkHttpLogInterceptor()) // 配置日志拦截器
                .build();
    }
}
