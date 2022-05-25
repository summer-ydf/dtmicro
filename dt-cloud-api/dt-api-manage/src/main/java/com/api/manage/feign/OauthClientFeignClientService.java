package com.api.manage.feign;

import com.api.manage.factory.OauthClientFeignClientFallback;
import com.cms.common.tool.domain.SysOauthClientVoEntity;
import com.cms.common.tool.result.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.api.common.ApiConstants.APPLICATION_MANAGE_API_AFFIX;
import static com.api.common.ApiConstants.APPLICATION_NAME_MANAGE;

/**
 * @author DT辰白 Created by 2022/4/28 14:51
 */
@Service
@FeignClient(value = APPLICATION_NAME_MANAGE, fallbackFactory = OauthClientFeignClientFallback.class)
public interface OauthClientFeignClientService {

    @GetMapping(value = APPLICATION_MANAGE_API_AFFIX + "/getOauthClientByClientId")
    ResultUtil<SysOauthClientVoEntity> getOauthClientByClientId(@RequestParam String clientId);
}
