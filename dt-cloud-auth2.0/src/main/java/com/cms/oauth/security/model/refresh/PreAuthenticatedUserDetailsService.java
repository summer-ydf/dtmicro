package com.cms.oauth.security.model.refresh;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cms.common.tool.enums.AuthenticationIdentityEnum;
import com.cms.common.tool.enums.IBaseEnum;
import com.cms.oauth.service.impl.SysUserDetailsServiceImpl;
import com.nimbusds.jose.JWSObject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * 刷新Token令牌，重新认证授权
 * @author ydf Created by 2022/4/28 17:11
 */
@NoArgsConstructor
public class PreAuthenticatedUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    /**
     * 客户端ID和用户服务 UserDetailService 的映射
     */
    private Map<String, UserDetailsService> userDetailsServiceMap;

    public PreAuthenticatedUserDetailsService(Map<String, UserDetailsService> userDetailsServiceMap) {
        Assert.notNull(userDetailsServiceMap, "userDetailsService cannot be null.");
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsServiceMap, "UserDetailsService must be set");
    }

    /**
     * 重写PreAuthenticatedAuthenticationProvider 的 preAuthenticatedUserDetailsService 属性，可根据客户端和认证方式选择用户服务 UserDetailService 获取用户信息 UserDetail
     *
     * @param authentication
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        String clientId = getOAuth2ClientId();
        // 获取认证身份标识，默认是用户名:username
        AuthenticationIdentityEnum authenticationIdentityEnum = IBaseEnum.getEnumByValue(getAuthenticationIdentity(), AuthenticationIdentityEnum.class);
        String authenticationIdentity = getAuthenticationIdentity();
        System.out.println("获取到用户身份标识："+authenticationIdentityEnum);
        System.out.println("获取到用户身份标识："+authenticationIdentity);
        System.out.println("获取到clientID："+clientId);

        UserDetailsService userDetailsService = userDetailsServiceMap.get(clientId);
        SysUserDetailsServiceImpl sysUserDetailsService = (SysUserDetailsServiceImpl) userDetailsService;
        System.out.println("authentication.getName():"+authentication.getName());
        return sysUserDetailsService.loadUserByUsername(authentication.getName());
//        if (clientId.equals(SecurityConstants.APP_CLIENT_ID)) {
//            // 移动端的用户体系是会员，认证方式是通过手机号 mobile 认证
//            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
//            switch (authenticationIdentityEnum) {
//                case MOBILE:
//                    return memberUserDetailsService.loadUserByMobile(authentication.getName());
//                default:
//                    return memberUserDetailsService.loadUserByUsername(authentication.getName());
//            }
//        } else if (clientId.equals(SecurityConstants.WEAPP_CLIENT_ID)) {
//            // 小程序的用户体系是会员，认证方式是通过微信三方标识 openid 认证
//            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
//            switch (authenticationIdentityEnum) {
//                case OPENID:
//                    return memberUserDetailsService.loadUserByOpenId(authentication.getName());
//                default:
//                    return memberUserDetailsService.loadUserByUsername(authentication.getName());
//            }
//        } else if (clientId.equals(SecurityConstants.ADMIN_CLIENT_ID)) {
//            // 管理系统的用户体系是系统用户，认证方式通过用户名 username 认证
//            switch (authenticationIdentityEnum) {
//                default:
//                    return userDetailsService.loadUserByUsername(authentication.getName());
//            }
//        } else {
//
//        }
    }

    /**
     * 获取登录认证的客户端ID
     * <p>
     * 兼容两种方式获取OAuth2客户端信息（client_id、client_secret）
     * 方式一：client_id、client_secret放在请求路径中
     * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
     *
     * @return
     */
    @SneakyThrows
    public static String getOAuth2ClientId() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 从请求路径中获取
        String clientId = request.getParameter("client_id");
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }

        System.out.println("获取clientId1："+clientId);

        // 从请求头获取
        String basic = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(basic) && basic.startsWith("Basic ")) {
            basic = basic.replace("Basic ", Strings.EMPTY);
            String basicPlainText = new String(Base64.getDecoder().decode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            clientId = basicPlainText.split(":")[0]; //client:secret
        }
        System.out.println("获取clientId2："+clientId);
        return clientId;
    }

    /**
     * 解析JWT获取获取认证身份标识
     * @return 认证身份标识
     */
    @SneakyThrows
    public static String getAuthenticationIdentity() {
        System.out.println("解析JWT获取获取认证身份标识===================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String refreshToken = request.getParameter("refresh_token");
        System.out.println("refreshToken:"+refreshToken);
        String payload = StrUtil.toString(JWSObject.parse(refreshToken).getPayload());
        System.out.println("payload:"+payload);
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        String authenticationIdentity = jsonObject.getStr("username");
        if (StrUtil.isBlank(authenticationIdentity)) {
            authenticationIdentity = AuthenticationIdentityEnum.USERNAME.getValue();
        }
        return authenticationIdentity;
    }
}
