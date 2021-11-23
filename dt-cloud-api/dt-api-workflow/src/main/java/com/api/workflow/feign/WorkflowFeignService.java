package com.api.workflow.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = "cms-service-flow",fallbackFactory = WorkflowFeignClientFallback.class)
public interface WorkflowFeignService {

    @GetMapping(value = "/flow/getPort")
    String getFlowPort();
}
