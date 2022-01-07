package com.cms.auth.service;

import com.api.manage.feign.OauthFeignClientService;
import com.cms.auth.domain.SecurityUser;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        HttpServletRequest request= currentRequest();
        System.out.println("登录请求->>>");
        System.out.println(request);
//        SecurityClaimsParams params= (SecurityClaimsParams) request.getAttribute(SecurityClaimsParams.class.getName());
//        if(params==null) {
//            throw new UsernameNotFoundException("未找到登录请求");
//        }
//        R<SecurityClaimsUser> userR = feignIcplClient.loadUserByUsername(params.getScope(),params.getSchool(),username,params.getOpenid());
//        if(!userR.isNotNull()) throw new UsernameNotFoundException(userR.getMessage());
//        return SecurityUser.from(userR.getData());
        ResultUtil<SecurityClaimsUser> claimsUserResultUtil = oauthFeignClientService.loadUserByUsername(username);
        System.out.println("远程调用回调结果->>>");
        System.out.println(claimsUserResultUtil);
        if (!claimsUserResultUtil.isSuccess()) {
            throw new UsernameNotFoundException(claimsUserResultUtil.getMessage());
        }
        return SecurityUser.from(claimsUserResultUtil.getData());
    }

    public static HttpServletRequest  currentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

}
