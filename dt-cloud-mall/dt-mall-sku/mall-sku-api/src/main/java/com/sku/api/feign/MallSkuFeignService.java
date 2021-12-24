package com.sku.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = "cms-mall-sku")
public interface MallSkuFeignService {

    @GetMapping(value = "sku/reduceStock")
    void reduceStock(@RequestParam Integer id);

}
