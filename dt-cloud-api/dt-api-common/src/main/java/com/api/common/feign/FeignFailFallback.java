package com.api.common.feign;

import com.cms.common.tool.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;

/**
 * @author DT辰白 Created by 2021/12/16 10:44
 */
public interface FeignFailFallback {

    Logger log = LoggerFactory.getLogger(FeignFailFallback.class);

    default <T> ResultUtil<T> fail(Throwable throwable) {
        log.info("远程调用失败统一处理返回:{},{}",throwable.getCause(),throwable.getMessage());
        if (throwable.getCause() instanceof SocketTimeoutException) {
            return ResultUtil.error("远程调用服务超时");
        }
        return ResultUtil.error("服务端服务未启动");
    }

}
