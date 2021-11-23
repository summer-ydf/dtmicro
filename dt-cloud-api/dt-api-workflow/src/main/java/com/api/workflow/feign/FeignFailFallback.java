package com.api.workflow.feign;

/**
 * @author ydf Created by 2021/11/23 16:16
 */
public interface FeignFailFallback {

//    default <T> R<T> fail(){
//        return R.fail("业务模块未加载");
//    }

    default String fail() {
        return "业务模块未加载";
    }
}
