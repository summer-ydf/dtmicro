package com.api.manage.feign;

import com.api.manage.factory.LogFeignClientFallback;
import com.cms.common.tool.domain.SysLoginLogVoEntity;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.api.common.ApiConstants.APPLICATION_MANAGE_API_AFFIX;
import static com.api.common.ApiConstants.APPLICATION_NAME_MANAGE;

/**
 * @author DT辰白 Created by 2022/2/11 17:39
 */
@Service
@FeignClient(value = APPLICATION_NAME_MANAGE, fallbackFactory = LogFeignClientFallback.class)
public interface LogFeignClientService {

    @PostMapping(value = APPLICATION_MANAGE_API_AFFIX + "/saveOprLog")
    ResultUtil<SysOperatorLogVoEntity> saveOprLog(@RequestBody SysOperatorLogVoEntity sysOperatorLogVoEntity);

    @PostMapping(value = APPLICATION_MANAGE_API_AFFIX + "/findLoginLogCount")
    ResultUtil<Long> findLoginLogCount();
}
