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

    private final Logger LOGGER = LoggerFactory.getLogger(ManageFeignClientFallback.class);

    @Override
    public ManageFeignService create(Throwable throwable) {
        LOGGER.info("调用接口请求出错：{}",throwable.getMessage());
        return new ManageFeignService() {

            @Override
            public void deductProduct(Integer a, String xid) {
                LOGGER.info("扣减库存服务未启用==============================");
            }

        };
    }
}
