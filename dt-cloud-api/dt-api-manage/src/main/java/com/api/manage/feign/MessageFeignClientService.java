package com.api.manage.feign;

import com.api.manage.factory.MessageFeignClientFallback;
import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.result.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.api.common.ApiConstants.APPLICATION_MANAGE_API_AFFIX;
import static com.api.common.ApiConstants.APPLICATION_NAME_MANAGE;

/**
 * @author ydf Created by 2022/4/13 17:44
 */
@Service
@FeignClient(value = APPLICATION_NAME_MANAGE, fallbackFactory = MessageFeignClientFallback.class)
public interface MessageFeignClientService {

    @PostMapping(value = APPLICATION_MANAGE_API_AFFIX + "/saveMqMessage")
    ResultUtil<SysMqMessageVoEntity> saveMqMessage(@RequestBody SysMqMessageVoEntity sysMqMessageVoEntity);
}
