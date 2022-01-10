package com.api.manage.factory;

import com.api.common.feign.FeignFailFallback;
import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author ydf Created by 2022/1/7 16:25
 */
@Component
public class OauthFeignClientFallback implements FeignFailFallback, FallbackFactory<OauthFeignClientService> {

    private final Logger LOGGER = LoggerFactory.getLogger(OauthFeignClientFallback.class);

    @Override
    public OauthFeignClientService create(Throwable throwable) {
        LOGGER.info("调用接口请求出错：{}",throwable.getMessage());
        return new OauthFeignClientService() {
            @Override
            public ResultUtil<SecurityClaimsUser> loadUserByUsername(String username) {
                return fail();
            }
        };
    }
}
