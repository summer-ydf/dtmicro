package com.cms.oauth.service.impl;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.oauth.domain.SecurityClaimsParams;
import com.cms.oauth.domain.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author DT
 * @date 2022/4/26 20:07
 */
@Service("memberUserDetailsService")
public class MemberUserDetailsServiceImpl implements UserDetailsService {

    private final OauthFeignClientService oauthFeignClientService;

    public MemberUserDetailsServiceImpl(OauthFeignClientService oauthFeignClientService) {
        this.oauthFeignClientService = oauthFeignClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByMobile(String mobile) {
        System.out.println("通过手机号码登录:"+mobile);
        SecurityClaimsParams claimsParams = SecurityClaimsParams.builder().scope("web").username(mobile).build();
        SysCmsUtils.log.info("远程调用设置参数->>>" + claimsParams);
        ResultUtil<SecurityClaimsUserEntity> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(mobile,claimsParams.getScope());
        SysCmsUtils.log.info("远程调用回调结果->>>" + claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }

    public UserDetails loadUserByOpenId(String openid) {
        System.out.println("通过微信号登录:"+openid);
        SecurityClaimsParams claimsParams = SecurityClaimsParams.builder().scope("web").username(openid).build();
        SysCmsUtils.log.info("远程调用设置参数->>>" + claimsParams);
        ResultUtil<SecurityClaimsUserEntity> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(openid,claimsParams.getScope());
        SysCmsUtils.log.info("远程调用回调结果->>>" + claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }
}
