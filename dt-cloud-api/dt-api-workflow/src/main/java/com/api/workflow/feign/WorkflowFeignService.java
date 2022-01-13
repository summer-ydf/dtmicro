package com.api.workflow.feign;

import com.cms.common.result.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * fallbackFactory = WorkflowFeignClientFallback.class
 * @author ydf Created by 2021/11/23 15:46
 */
@Service
@FeignClient(value = "cms-service-flow")
public interface WorkflowFeignService {

    @GetMapping(value = "/flow/getPort/{userId}")
    ResultUtil<?> getFlowPort(@PathVariable String userId);

    @PostMapping(value = "/flow/savePort")
    String savePort();

}
