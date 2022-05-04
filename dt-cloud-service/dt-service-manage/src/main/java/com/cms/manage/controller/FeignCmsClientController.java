package com.cms.manage.controller;

import com.api.manage.feign.LogFeignClientService;
import com.api.manage.feign.MessageFeignClientService;
import com.api.manage.feign.OauthClientFeignClientService;
import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.domain.SysOauthClientVoEntity;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.entity.SysLogOperatorEntity;
import com.cms.manage.entity.SysOauthClientEntity;
import com.cms.manage.service.MessageService;
import com.cms.manage.service.SysLogLoginService;
import com.cms.manage.service.SysLogOperatorService;
import com.cms.manage.service.SysOauthClientService;
import com.cms.manage.service.SysOperatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import static com.cms.common.tool.enums.AuthenticationIdentityEnum.*;

/**
 * @author ydf Created by 2022/1/7 16:20
 */
@Api(tags = "远程调用接口API")
@RestController
public class FeignCmsClientController implements OauthFeignClientService, LogFeignClientService, MessageFeignClientService, OauthClientFeignClientService {

    private final SysLogLoginService sysLogLoginService;
    private final SysLogOperatorService sysOperatorLogService;
    private final SysOperatorService sysOperatorService;
    private final MessageService mqMessageService;
    private final SysOauthClientService sysOauthClientService;

    public FeignCmsClientController(SysOperatorService sysOperatorService, SysLogOperatorService sysOperatorLogService, SysLogLoginService sysLogLoginService, MessageService mqMessageService, SysOauthClientService sysOauthClientService) {
        this.sysOperatorService = sysOperatorService;
        this.sysOperatorLogService = sysOperatorLogService;
        this.sysLogLoginService = sysLogLoginService;
        this.mqMessageService = mqMessageService;
        this.sysOauthClientService = sysOauthClientService;
    }

    @Override
    public ResultUtil<SecurityClaimsUserEntity> loadUserByUsername(String username) {
        return sysOperatorService.oauthAuthenticationByAccount(username,USERNAME, null);
    }

    @Override
    public ResultUtil<SecurityClaimsUserEntity> loadUserByMobile(String mobile) {
        return sysOperatorService.oauthAuthenticationByAccount(mobile,MOBILE, null);
    }

    @Override
    public ResultUtil<SecurityClaimsUserEntity> loadUserByIdCardAndName(String idno, String name) {
        return sysOperatorService.oauthAuthenticationByAccount(idno,IDCARD, name);
    }

    @Override
    public ResultUtil<SecurityClaimsUserEntity> loadUserByOpenId(String openid) {
        return sysOperatorService.oauthAuthenticationByAccount(openid,OPENID, null);
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

    @Override
    public ResultUtil<SysOauthClientVoEntity> getOauthClientByClientId(String clientId) {
        SysOauthClientEntity client = sysOauthClientService.getById(clientId);
        if (ObjectUtils.isEmpty(client)) {
            return ResultUtil.error("OAuth2 客户端不存在");
        }
        SysOauthClientVoEntity sysOauthClientVo = new SysOauthClientVoEntity();
        BeanUtils.copyProperties(client,sysOauthClientVo);
        return ResultUtil.success(sysOauthClientVo);
    }
}
