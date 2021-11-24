package com.api.workflow.factory;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.api.workflow.feign.WorkflowFeignService;
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
            public String getFlowPort(String userId) {
                log.info("服务降级!",throwable);
                return fail();
            }

            @Override
            public String savePort() {
                return fail();
            }
        };
    }
}
