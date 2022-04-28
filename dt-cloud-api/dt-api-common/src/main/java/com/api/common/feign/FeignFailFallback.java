package com.api.common.feign;

import com.cms.common.tool.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ydf Created by 2021/12/16 10:44
 */
public interface FeignFailFallback {

    Logger log = LoggerFactory.getLogger(FeignFailFallback.class);

    default <T> ResultUtil<T> fail() {
        log.info("调用失败统一处理返回：服务未开启====================");
        return ResultUtil.error("管理端服务未启动");
    }

}
