package com.api.item.factory;

import com.api.common.feign.FeignFailFallback;
import com.api.item.feign.ItemFeignService;
import com.cms.common.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断工厂
 * @author ydf Created by 2021/11/23 16:05
 */
@Component
public class ItemFeignClientFallback implements FeignFailFallback,FallbackFactory<ItemFeignService> {

    Logger log = LoggerFactory.getLogger(ItemFeignClientFallback.class);

    @Override
    public ItemFeignService create(Throwable throwable) {
        log.info("调用接口请求出错：{}",throwable.getMessage());
        return new ItemFeignService() {

            @Override
            public ResultUtil<?> save(Integer a) {
                System.out.println("失败->>>");
                return null;
            }
        };
    }
}
