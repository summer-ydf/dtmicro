package com.cms.oauth.service.impl;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.core.utils.CoreWebUtils;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.oauth.domain.SecurityClaimsParams;
import com.cms.oauth.domain.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * PC端认证方式
 * @author ydf Created by 2022/4/25 18:59
 */
@Slf4j
@Service("sysUserDetailsService")
public class SysUserDetailsServiceImpl implements UserDetailsService {

    private final OauthFeignClientService oauthFeignClientService;

    public SysUserDetailsServiceImpl(OauthFeignClientService oauthFeignClientService) {
        this.oauthFeignClientService = oauthFeignClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = CoreWebUtils.currentRequest();
//        SecurityClaimsParams params = (SecurityClaimsParams) request.getAttribute(SecurityClaimsParams.class.getName());
//        if(ObjectUtils.isEmpty(params)) {
//            throw new UsernameNotFoundException("缺少登录附加参数");
//        }
        log.info("Oauth2 登录认证中,账号授权模式【{}】",username);
        SecurityClaimsParams claimsParams = SecurityClaimsParams.builder().scope("web").username(username).build();
        log.info("远程调用设置参数->>>" + claimsParams);
        ResultUtil<SecurityClaimsUserEntity> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(username,claimsParams.getScope());
        log.info("Oauth2 授权回调结果【{}】",claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }
}

