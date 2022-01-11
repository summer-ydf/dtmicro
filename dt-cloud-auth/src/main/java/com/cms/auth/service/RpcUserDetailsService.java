package com.cms.auth.service;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.auth.domain.SecurityUser;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import com.cms.common.utils.SysCmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2022/1/7 16:00
 */
@Service
public class RpcUserDetailsService implements UserDetailsService {

    @Autowired
    private OauthFeignClientService oauthFeignClientService;

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResultUtil<SecurityClaimsUser> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(username);
        SysCmsUtils.log.info("远程调用回调结果->>>" + claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }

}
