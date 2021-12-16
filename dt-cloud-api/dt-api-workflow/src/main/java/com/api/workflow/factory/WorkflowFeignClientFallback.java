package com.api.workflow.factory;

import com.api.common.feign.FeignFailFallback;
import com.api.workflow.feign.WorkflowFeignService;
import com.cms.common.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断工厂
 * @author ydf Created by 2021/11/23 16:05
 */
@Component
public class WorkflowFeignClientFallback implements FeignFailFallback,FallbackFactory<WorkflowFeignService> {

    Logger log = LoggerFactory.getLogger(WorkflowFeignClientFallback.class);

    @Override
    public WorkflowFeignService create(Throwable throwable) {
        log.info("调用接口请求出错：{}",throwable.getMessage());
        return new WorkflowFeignService() {

            @Override
            public ResultUtil<?> getFlowPort(String userId) {
                log.info("服务降级!",throwable);
                return fail();
            }

            @Override
            public String savePort() {
                return "添加出错";
            }
        };
    }
}
