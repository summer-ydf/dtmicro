package com.user.api.feign;

import com.user.api.factory.MallUserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = "cms-mall-user",fallbackFactory = MallUserFeignClientFallback.class)
public interface MallUserFeignService {

    @GetMapping(value = "user/reduceMoney")
    void reduceMoney(@RequestParam Integer id);

}
