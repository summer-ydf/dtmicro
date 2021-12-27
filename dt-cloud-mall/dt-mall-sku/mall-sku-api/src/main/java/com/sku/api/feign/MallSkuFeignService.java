package com.sku.api.feign;

import com.cms.common.result.ResultUtil;
import com.sku.api.factory.MallSkuFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = "cms-mall-sku",fallbackFactory = MallSkuFeignClientFallback.class)
public interface MallSkuFeignService {

    @GetMapping(value = "sku/reduceStock")
    ResultUtil<?> reduceStock(@RequestParam Integer id);

}
