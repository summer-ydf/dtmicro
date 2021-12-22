package com.api.manage.feign;

import com.api.manage.factory.ManageFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.api.common.ApiConstants.APPLICATION_MANAGE_API_AFFIX;
import static com.api.common.ApiConstants.APPLICATION_NAME_MANAGE;

/**
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = APPLICATION_NAME_MANAGE,fallbackFactory = ManageFeignClientFallback.class)
public interface ManageFeignService {

    @GetMapping(value = APPLICATION_MANAGE_API_AFFIX + "/loadUserByUsername")
    String loadUserByUsername(@RequestParam String username);

    @GetMapping(value = APPLICATION_MANAGE_API_AFFIX + "/deductProduct")
    void deductProduct(@RequestParam Integer a);
}
