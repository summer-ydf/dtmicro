package com.cms.oauth.service.impl;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.core.utils.CoreWebUtils;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.oauth.domain.SecurityClaimsParams;
import com.cms.oauth.domain.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * UserDetailService自定义实现加载用户认证信息
 * @author DT
 * @date 2022/4/25 18:59
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final OauthFeignClientService oauthFeignClientService;

    public UserDetailsServiceImpl(OauthFeignClientService oauthFeignClientService) {
        this.oauthFeignClientService = oauthFeignClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = CoreWebUtils.currentRequest();
//        SecurityClaimsParams params = (SecurityClaimsParams) request.getAttribute(SecurityClaimsParams.class.getName());
//        if(ObjectUtils.isEmpty(params)) {
//            throw new UsernameNotFoundException("缺少登录附加参数");
//        }
        SecurityClaimsParams claimsParams = SecurityClaimsParams.builder().scope("web").username(username).build();
        SysCmsUtils.log.info("远程调用设置参数->>>" + claimsParams);
        ResultUtil<SecurityClaimsUserEntity> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(username,claimsParams.getScope());
        SysCmsUtils.log.info("远程调用回调结果->>>" + claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }
}

