package com.api.workflow.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ydf Created by 2021/11/23 16:16
 */
public interface FeignFailFallback {

    Logger log = LoggerFactory.getLogger(FeignFailFallback.class);

//    default <T> R<T> fail(){
//        return R.fail("业务模块未加载");
//    }

    default String fail() {
        log.info("调用失败统一处理返回：服务未开启====================");
        return "业务模块未加载";
    }
}
