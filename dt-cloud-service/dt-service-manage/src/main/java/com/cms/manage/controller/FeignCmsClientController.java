package com.cms.manage.controller;

import com.api.manage.feign.LogFeignClientService;
import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorLogEntity;
import com.cms.manage.service.SysOperatorLogService;
import com.cms.manage.service.SysOperatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2022/1/7 16:20
 */
@Api(tags = "远程调用接口API")
@RestController
public class FeignCmsClientController implements OauthFeignClientService, LogFeignClientService {

    private final SysOperatorLogService sysOperatorLogService;
    private final SysOperatorService sysOperatorService;

    public FeignCmsClientController(SysOperatorService sysOperatorService, SysOperatorLogService sysOperatorLogService) {
        this.sysOperatorService = sysOperatorService;
        this.sysOperatorLogService = sysOperatorLogService;
    }

    @Override
    public ResultUtil<SecurityClaimsUserEntity> loadUserByUsername(String username, String scope) {
        return sysOperatorService.loadUserByUsername(username,scope);
    }

    @Override
    public ResultUtil<SysOperatorLogVoEntity> saveOprLog(SysOperatorLogVoEntity sysOperatorLogVoEntity) {
        System.out.println("添加数据成功===============");
        SysOperatorLogEntity sysOperatorLogEntity = new SysOperatorLogEntity();
        BeanUtils.copyProperties(sysOperatorLogVoEntity,sysOperatorLogEntity);
        System.out.println(sysOperatorLogEntity);
//        return sysOperatorLogService.saveOperatorLog(sysOperatorLogVoEntity);
        return ResultUtil.success();
    }
}
