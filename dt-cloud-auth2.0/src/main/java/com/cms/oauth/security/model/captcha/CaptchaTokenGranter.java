package com.cms.oauth.security.model.captcha;

import com.cms.oauth.security.exception.ParameterAuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

import static com.cms.common.tool.constant.ConstantCode.CACHE_CODE_KEY;

/**
 * 验证码授权者
 * @author ydf Created by 2022/4/26 16:05
 */
public class CaptchaTokenGranter extends AbstractTokenGranter {

    /**
     * 声明授权者CaptchaTokenGranter支持授权模式：captcha
     * 根据接口传值grant_type=captcha的值匹配到此授权者
     * 匹配逻辑详见下面的两个方法
     * @see org.springframework.security.oauth2.provider.CompositeTokenGranter#grant(String, TokenRequest)
     * @see org.springframework.security.oauth2.provider.token.AbstractTokenGranter#grant(String, TokenRequest)
     */
    private static final String GRANT_TYPE = "captcha";
    private final AuthenticationManager authenticationManager;
    private RedisTemplate<String, Object> redisTemplate;

    public CaptchaTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                               OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager,
                               RedisTemplate<String, Object> redisTemplate) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        // 验证码校验逻辑
        String validateCode = parameters.get("code");
        System.out.println("输入验证码："+validateCode);
        if (StringUtils.isNotBlank(validateCode)) {
            // 校验验证码是否正确
            checkVerificationCode(validateCode);
        }else {
            throw new ParameterAuthenticationException("验证码不能为空");
        }

        String username = parameters.get("username");
        String password = parameters.get("password");

        // 移除后续无用参数
        parameters.remove("username");
        parameters.remove("password");
        parameters.remove("code");

        // 和密码模式一样的逻辑
        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
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
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }
    }

    private void checkVerificationCode(String code) {
        final String redisKey = (CACHE_CODE_KEY + code).toLowerCase();
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (code.equals(redisValue)) {
            // 验证通过，删除缓存
            redisTemplate.delete(redisKey);
        } else {
            throw new ParameterAuthenticationException("您输入的验证码不正确");
        }
    }
}
