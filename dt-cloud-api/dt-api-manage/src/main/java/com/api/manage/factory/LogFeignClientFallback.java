package com.api.manage.factory;

import com.api.common.feign.FeignFailFallback;
import com.api.manage.feign.LogFeignClientService;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author DT辰白 Created by 2022/1/7 16:25
 */
@Component
public class LogFeignClientFallback implements FeignFailFallback, FallbackFactory<LogFeignClientService> {

    private final Logger LOGGER = LoggerFactory.getLogger(LogFeignClientFallback.class);

    @Override
    public LogFeignClientService create(Throwable throwable) {
        LOGGER.info("调用操作日志接口请求出错：{}",throwable.getMessage());
        return new LogFeignClientService() {
            @Override
            public ResultUtil<SysOperatorLogVoEntity> saveOprLog(SysOperatorLogVoEntity sysOperatorLogVoEntity) {
                return fail(throwable);
            }
            @Override
            public ResultUtil<Long> findLoginLogCount() {
                return fail(throwable);
            }
        };
    }
}
