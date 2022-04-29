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
 * 小程序微信端认证方式
 * @author ydf Created by 2022/4/25 18:59
 */
@Slf4j
@Service("wechatUserDetailsService")
public class WechatUserDetailsServiceImpl implements UserDetailsService {

    private final OauthFeignClientService oauthFeignClientService;

    public WechatUserDetailsServiceImpl(OauthFeignClientService oauthFeignClientService) {
        this.oauthFeignClientService = oauthFeignClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByOpenId(String openid) {
        log.info("Oauth2 登录认证中,小程序微信授权模式【{}】",openid);
        SecurityClaimsParams claimsParams = SecurityClaimsParams.builder().scope("web").username(openid).build();
        log.info("远程调用设置参数->>>" + claimsParams);
        ResultUtil<SecurityClaimsUserEntity> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(openid,claimsParams.getScope());
        log.info("Oauth2 授权回调结果【{}】",claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }
}
