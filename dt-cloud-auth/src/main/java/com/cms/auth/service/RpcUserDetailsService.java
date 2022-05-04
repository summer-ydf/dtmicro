package com.cms.auth.service;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.auth.domain.SecurityClaimsParams;
import com.cms.auth.domain.SecurityUser;
import com.cms.common.core.utils.CoreWebUtils;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证
 * @author ydf Created by 2022/4/25 18:59
 */
@Slf4j
@Service
public class RpcUserDetailsService implements UserDetailsService {

    private final OauthFeignClientService oauthFeignClientService;

    public RpcUserDetailsService(OauthFeignClientService oauthFeignClientService) {
        this.oauthFeignClientService = oauthFeignClientService;
    }

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Oauth2 登录认证中,账号授权模式【{}】",username);

        HttpServletRequest request = CoreWebUtils.currentRequest();
        SecurityClaimsParams params = (SecurityClaimsParams) request.getAttribute(SecurityClaimsParams.class.getName());
        if(ObjectUtils.isEmpty(params)) {
            throw new UsernameNotFoundException("缺少登录附加参数");
        }

        log.info("Oauth2 登录认证中,远程调用设置参数【{}】",params);

        ResultUtil<SecurityClaimsUserEntity> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(username);

        log.info("Oauth2 授权回调结果【{}】",claimsUserResultUtil);

        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }
}
