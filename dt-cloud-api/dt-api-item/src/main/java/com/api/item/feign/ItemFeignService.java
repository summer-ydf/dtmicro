package com.api.item.feign;

import com.api.item.factory.ItemFeignClientFallback;
import com.cms.common.result.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.api.common.ApiConstants.APPLICATION_ITEM_API_AFFIX;
import static com.api.common.ApiConstants.APPLICATION_NAME_ITEM;

/**
 * // 分布式事务调用的接口，不能使用fallbackFactory = ItemFeignClientFallback.class 处理，否则事务会失效
 * // sh nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -t seata_namespace_id -u nacos -w nacos
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = APPLICATION_NAME_ITEM)
public interface ItemFeignService {

    @GetMapping(value = APPLICATION_ITEM_API_AFFIX + "/save")
    ResultUtil<?> save(@RequestParam Integer a);
}
