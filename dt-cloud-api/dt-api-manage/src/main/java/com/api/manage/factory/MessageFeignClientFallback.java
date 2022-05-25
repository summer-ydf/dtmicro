package com.api.manage.factory;

import com.api.common.feign.FeignFailFallback;
import com.api.manage.feign.MessageFeignClientService;
import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.result.ResultUtil;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author DT辰白 Created by 2022/1/7 16:25
 */
@Component
public class MessageFeignClientFallback implements FeignFailFallback, FallbackFactory<MessageFeignClientService> {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageFeignClientFallback.class);

    @Override
    public MessageFeignClientService create(Throwable throwable) {
        LOGGER.info("调用消息发送保存接口请求出错：{}",throwable.getMessage());
        return new MessageFeignClientService() {

            @Override
            public ResultUtil<SysMqMessageVoEntity> saveMqMessage(SysMqMessageVoEntity sysMqMessageVoEntity) {
                return fail(throwable);
            }
        };
    }
}
