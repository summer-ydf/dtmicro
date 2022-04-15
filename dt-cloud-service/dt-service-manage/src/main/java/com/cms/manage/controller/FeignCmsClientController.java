package com.cms.manage.controller;

import com.api.manage.feign.LogFeignClientService;
import com.api.manage.feign.MessageFeignClientService;
import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.entity.SysLogOperatorEntity;
import com.cms.manage.service.MqMessageService;
import com.cms.manage.service.SysLogLoginService;
import com.cms.manage.service.SysLogOperatorService;
import com.cms.manage.service.SysOperatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2022/1/7 16:20
 */
@Api(tags = "远程调用接口API")
@RestController
public class FeignCmsClientController implements OauthFeignClientService, LogFeignClientService, MessageFeignClientService {

    private final SysLogLoginService sysLogLoginService;
    private final SysLogOperatorService sysOperatorLogService;
    private final SysOperatorService sysOperatorService;
    private final MqMessageService mqMessageService;

    public FeignCmsClientController(SysOperatorService sysOperatorService, SysLogOperatorService sysOperatorLogService, SysLogLoginService sysLogLoginService, MqMessageService mqMessageService) {
        this.sysOperatorService = sysOperatorService;
        this.sysOperatorLogService = sysOperatorLogService;
        this.sysLogLoginService = sysLogLoginService;
        this.mqMessageService = mqMessageService;
    }

    @Override
    public ResultUtil<SecurityClaimsUserEntity> loadUserByUsername(String username, String scope) {
        return sysOperatorService.loadUserByUsername(username,scope);
    }

    @Override
    public ResultUtil<SysOperatorLogVoEntity> saveOprLog(SysOperatorLogVoEntity sysOperatorLogVoEntity) {
        SysLogOperatorEntity sysOperatorLogEntity = new SysLogOperatorEntity();
        BeanUtils.copyProperties(sysOperatorLogVoEntity,sysOperatorLogEntity);
        return sysOperatorLogService.saveOperatorLog(sysOperatorLogEntity);
    }

    @Override
    public ResultUtil<Long> findLoginLogCount() {
        return ResultUtil.success(sysLogLoginService.count());
    }

    @Override
    public ResultUtil<SysMqMessageVoEntity> saveMqMessage(SysMqMessageVoEntity sysMqMessageVoEntity) {
        MqMessageEntity mqMessageEntity = new MqMessageEntity();
        BeanUtils.copyProperties(sysMqMessageVoEntity,mqMessageEntity);
        mqMessageService.saveMqMessage(mqMessageEntity);
        return ResultUtil.success();
    }
}
