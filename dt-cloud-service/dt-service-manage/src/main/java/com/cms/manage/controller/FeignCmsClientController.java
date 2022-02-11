package com.cms.manage.controller;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.service.SysOperatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2022/1/7 16:20
 */
@Api(tags = "远程调用接口API")
@RestController
public class FeignCmsClientController implements OauthFeignClientService {

    private final SysOperatorService sysOperatorService;

    public FeignCmsClientController(SysOperatorService sysOperatorService) {
        this.sysOperatorService = sysOperatorService;
    }

    @Override
    public ResultUtil<SecurityClaimsUserEntity> loadUserByUsername(String username, String scope) {
        return sysOperatorService.loadUserByUsername(username,scope);
    }

}
