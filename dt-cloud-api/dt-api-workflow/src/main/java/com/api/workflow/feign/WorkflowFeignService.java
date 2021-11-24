package com.api.workflow.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(name = "cms-service-flow",fallbackFactory = WorkflowFeignClientFallback.class)
public interface WorkflowFeignService {

    @GetMapping(value = "/flow/getPort/{userId}")
    String getFlowPort(@PathVariable String userId);

    @PostMapping(value = "/flow/savePort")
    String savePort();

}
