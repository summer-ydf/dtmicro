package com.api.manage.factory;

import com.api.common.feign.FeignFailFallback;
import com.api.manage.feign.OauthClientFeignClientService;
import com.cms.common.tool.domain.SysOauthClientVoEntity;
import com.cms.common.tool.result.ResultUtil;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DT辰白 Created by 2022/4/28 14:50
 */
public class OauthClientFeignClientFallback implements FeignFailFallback, FallbackFactory<OauthClientFeignClientService> {

    private final Logger LOGGER = LoggerFactory.getLogger(OauthClientFeignClientFallback.class);

    @Override
    public OauthClientFeignClientService create(Throwable throwable) {
        LOGGER.info("调用客户端接口请求出错：{}",throwable.getMessage());
        return new OauthClientFeignClientService() {
            @Override
            public ResultUtil<SysOauthClientVoEntity> getOauthClientByClientId(String clientId) {
                return fail(throwable);
            }
        };
    }
}
