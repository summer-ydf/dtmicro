package com.cms.oauth.security.model.refresh;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cms.common.core.utils.CoreWebUtils;
import com.cms.common.tool.enums.AuthenticationIdentityEnum;
import com.cms.common.tool.enums.IBaseEnum;
import com.cms.oauth.service.impl.IdCardUserDetailsServiceImpl;
import com.cms.oauth.service.impl.MemberUserDetailsServiceImpl;
import com.cms.oauth.service.impl.SysUserDetailsServiceImpl;
import com.cms.oauth.service.impl.WechatUserDetailsServiceImpl;
import com.nimbusds.jose.JWSObject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.cms.common.tool.enums.AuthenticationIdentityEnum.*;

/**
 * 刷新Token令牌，重新认证授权
 * @author ydf Created by 2022/4/28 17:11
 */
@Slf4j
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
        Assert.notNull(this.userDetailsServiceMap, "UserDetailsService source must be set");
    }

    /**
     * 重写loadUserDetails方法
     * @param authentication 泛型参数
     * @return 返回认证实列对象信息
     * @throws UsernameNotFoundException 认证失败异常
     */
    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {

        String clientId = getOAuthClientId();

        AuthenticationIdentityEnum authenticationIdentityEnum = IBaseEnum.getEnumByValue(getAuthenticationIdentity(), AuthenticationIdentityEnum.class);

        UserDetailsService userDetailsService = userDetailsServiceMap.get(clientId);

        log.info("系统免密登录，刷新令牌重新认证，认证身份【{}】，认证参数【{}】,携带令牌ID【{}】",authenticationIdentityEnum, authentication.getName(), clientId);

        switch (authenticationIdentityEnum) {
            case USERNAME:
                // PC端认证
                SysUserDetailsServiceImpl sysUserDetailsService = (SysUserDetailsServiceImpl) userDetailsService;
                return sysUserDetailsService.loadUserByUsername(authentication.getName());
            case MOBILE: {
                // APP端
                MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
                return memberUserDetailsService.loadUserByMobile(authentication.getName());
            }
            case IDCARD:
                // TODO 身份证端
                IdCardUserDetailsServiceImpl idCardUserDetailsService = (IdCardUserDetailsServiceImpl) userDetailsService;
                return idCardUserDetailsService.loadUserByIdCard(authentication.getName(), null);
            case OPENID: {
                // 微信小程序端
                WechatUserDetailsServiceImpl wechatUserDetailsService = (WechatUserDetailsServiceImpl) userDetailsService;
                return wechatUserDetailsService.loadUserByOpenId(authentication.getName());
            }
            default:
                // 默认认证体系，通过username
                return userDetailsService.loadUserByUsername(authentication.getName());
        }
    }

    /**
     * 获取登录认证的客户端ID
     * @return 返回客户端ID
     */
    public static String getOAuthClientId() {
        HttpServletRequest request = CoreWebUtils.currentRequest();
        // 从请求路径中获取,如果经过加密处理的话，需要解密
        String clientId = request.getParameter("client_id");
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }
        return null;
    }

    /**
     * 解析JWT获取获取认证身份标识
     * @return 认证身份标识
     */
    @SneakyThrows
    public static String getAuthenticationIdentity() {
        HttpServletRequest request = CoreWebUtils.currentRequest();
        String refreshToken = request.getParameter("refresh_token");
        String payload = StrUtil.toString(JWSObject.parse(refreshToken).getPayload());
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        log.info("解析JWT获取获取认证身份标识【{}】",jsonObject);
        String authenticationIdentity = jsonObject.getStr("authenticationIdentity");
        if (StrUtil.isBlank(authenticationIdentity)) {
            authenticationIdentity = USERNAME.getValue();
        }
        return authenticationIdentity;
    }
}
