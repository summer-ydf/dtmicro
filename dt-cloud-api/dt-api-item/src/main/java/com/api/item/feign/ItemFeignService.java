package com.api.item.feign;

import com.api.item.factory.ItemFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.api.common.ApiConstants.APPLICATION_ITEM_API_AFFIX;
import static com.api.common.ApiConstants.APPLICATION_NAME_ITEM;

/**
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = APPLICATION_NAME_ITEM)
public interface ItemFeignService {

    @GetMapping(value = APPLICATION_ITEM_API_AFFIX + "/save")
    void save(@RequestParam Integer a);
}
