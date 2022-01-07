package com.cms.manage.controller;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import com.cms.manage.service.SysOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2022/1/7 16:20
 */
@RestController
public class FeignCmsClientController implements OauthFeignClientService {

    @Autowired
    private SysOperatorService sysOperatorService;

    @Override
    public ResultUtil<SecurityClaimsUser> loadUserByUsername(String username) {
        return sysOperatorService.loadUserByUsername(username);
    }

}
