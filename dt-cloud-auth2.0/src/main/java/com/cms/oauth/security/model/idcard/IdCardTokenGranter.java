package com.cms.oauth.security.model.idcard;

import com.cms.oauth.security.exception.ParameterAuthenticationException;
import com.cms.oauth.security.model.mobile.SmsCodeAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 身份证号码授权者
 * @author ydf Created by 2022/4/28 13:04
 */
public class IdCardTokenGranter extends AbstractTokenGranter {

    /**
     * 声明授权者IdCardTokenGranter支持授权模式id_card
     * 根据接口传值grant_type=id_card的值匹配到此授权者
     * 匹配逻辑详见下面的两个方法
     *
     * @see org.springframework.security.oauth2.provider.CompositeTokenGranter#grant(String, TokenRequest)
     * @see org.springframework.security.oauth2.provider.token.AbstractTokenGranter#grant(String, TokenRequest)
     */
    private static final String GRANT_TYPE = "id_card";
    private final AuthenticationManager authenticationManager;

    public IdCardTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                               OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        // 身份证号
        String idno = parameters.get("idno");
        // 姓名
        String name = parameters.get("name");

        System.out.println("身份证号码授权者："+idno);
        System.out.println("身份证号码授权者："+name);

        if (StringUtils.isBlank(idno)) {
            throw new ParameterAuthenticationException("身份证号码不能为空");
        }

        if (StringUtils.isBlank(name)) {
            throw new ParameterAuthenticationException("姓名不能为空");
        }

        Authentication userAuth = new IdCardAuthenticationToken(idno, name);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) {
            // 认证成功
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            // 认证失败
            throw new InvalidGrantException("Could not authenticate user: " + idno);
        }
    }
}
