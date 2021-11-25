package com.api.workflow.factory;

import com.cms.common.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ydf Created by 2021/11/23 16:16
 */
public interface FeignFailFallback {

    Logger log = LoggerFactory.getLogger(FeignFailFallback.class);

    default <T> ResultUtil<T> fail() {
        log.info("调用失败统一处理返回：服务未开启====================");
        return ResultUtil.error("业务模块未启动");
    }
}
