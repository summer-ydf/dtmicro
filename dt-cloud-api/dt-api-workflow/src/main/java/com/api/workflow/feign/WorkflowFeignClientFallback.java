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
    public WorkflowFeignService create(Throwable throwable) {
        System.out.println("调用接口请求出错->>>"+ throwable.getMessage());
        return new WorkflowFeignService() {

            @Override
            public String getFlowPort(String userId) {
                System.out.println("调用出错->>>");
                return fail();
            }

            @Override
            public String savePort() {
                return fail();
            }
        };
    }
}
