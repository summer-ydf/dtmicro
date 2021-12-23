package com.api.manage.factory;

import com.api.common.feign.FeignFailFallback;
import com.api.manage.feign.ManageFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断工厂
 * @author ydf Created by 2021/11/23 16:05
 */
@Component
public class ManageFeignClientFallback implements FeignFailFallback,FallbackFactory<ManageFeignService> {

    Logger log = LoggerFactory.getLogger(ManageFeignClientFallback.class);

    @Override
    public ManageFeignService create(Throwable throwable) {
        log.info("调用接口请求出错：{}",throwable.getMessage());
        return new ManageFeignService() {

            @Override
            public String loadUserByUsername(String username) {
                return error();
            }

            @Override
            public void deductProduct(Integer a, String xid) {
                log.info("扣减库存服务未启用==============================");
            }

        };
    }
}
