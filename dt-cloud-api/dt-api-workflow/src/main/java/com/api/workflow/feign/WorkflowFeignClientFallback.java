package com.api.workflow.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断工厂
 * @author ydf Created by 2021/11/23 16:05
 */
@Component
public class WorkflowFeignClientFallback implements FeignFailFallback , FallbackFactory<WorkflowFeignService> {

    @Override
    public WorkflowFeignService create(Throwable cause) {
        return new WorkflowFeignService() {
            @Override
            public String getFlowPort() {
                return fail();
            }
        };
    }
}
