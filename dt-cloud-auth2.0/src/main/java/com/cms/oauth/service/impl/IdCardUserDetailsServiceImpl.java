package com.cms.oauth.service.impl;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.oauth.domain.SecurityClaimsParams;
import com.cms.oauth.domain.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 身份证端认证方式
 * @author ydf Created by 2022/4/25 18:59
 */
@Slf4j
@Service("idCardUserDetailsService")
public class IdCardUserDetailsServiceImpl implements UserDetailsService {

    private final OauthFeignClientService oauthFeignClientService;

    public IdCardUserDetailsServiceImpl(OauthFeignClientService oauthFeignClientService) {
        this.oauthFeignClientService = oauthFeignClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByIdCard(String idno, String name) {
        log.info("Oauth2 登录认证中,身份证授权模式【{},{}】",idno,name);
        SecurityClaimsParams claimsParams = SecurityClaimsParams.builder().scope("web").username(idno).build();
        log.info("远程调用设置参数->>>" + claimsParams);
        ResultUtil<SecurityClaimsUserEntity> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(idno,claimsParams.getScope());
        log.info("Oauth2 授权回调结果【{}】",claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }
}
