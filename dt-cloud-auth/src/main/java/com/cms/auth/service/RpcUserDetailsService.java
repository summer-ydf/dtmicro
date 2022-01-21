package com.cms.auth.service;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.auth.domain.SecurityClaimsParams;
import com.cms.auth.domain.SecurityUser;
import com.cms.auth.utils.CoreWebUtils;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import com.cms.common.utils.SysCmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

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
        HttpServletRequest request = CoreWebUtils.currentRequest();
        SecurityClaimsParams params = (SecurityClaimsParams) request.getAttribute(SecurityClaimsParams.class.getName());
        if(ObjectUtils.isEmpty(params)) {
            throw new UsernameNotFoundException("缺少登录附加参数");
        }
        SysCmsUtils.log.info("远程调用设置参数->>>" + params);
        ResultUtil<SecurityClaimsUser> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(username,params.getScope());
        SysCmsUtils.log.info("远程调用回调结果->>>" + claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }
}
