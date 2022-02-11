package com.cms.auth.config.handler;

import com.cms.auth.config.exception.CmsOAuth2Exception;
import com.cms.auth.domain.SecurityClaimsParams;
import com.cms.common.core.utils.CoreWebUtils;
import com.cms.common.tool.constant.ConstantCommonCode;
import com.cms.common.tool.utils.SysCmsUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 账号密码错误异常处理
 * @author ydf Created by 2022/1/6 15:02
 */
public class TokenAuthenticationFailureHandler implements OAuth2AuthenticationFailureHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public CmsOAuth2Exception onAuthenticationFailure(OAuth2Exception oAuth2Exception) {
        CmsOAuth2Exception ex = new CmsOAuth2Exception(oAuth2Exception.getMessage(), oAuth2Exception);
        SysCmsUtils.log.info("账号密码错误异常处理：====================");
        try{
            HttpServletRequest request = CoreWebUtils.currentRequest();
            SecurityClaimsParams params = (SecurityClaimsParams) request.getAttribute(SecurityClaimsParams.class.getName());
            String username = params.getUsername();
            // 密码超过5次错误，用户冻结15分钟
            String loginCountStr = stringRedisTemplate.opsForValue().get(ConstantCommonCode.CACHE_LOGIN_LOCK + username);
            int lockCount = NumberUtils.toInt(loginCountStr,0) + 1;
            if(lockCount >= ConstantCommonCode.LOCK_TIME) {
                Date expDate = DateUtils.addMinutes(new Date(), ConstantCommonCode.LOCK_MINUTE);
                stringRedisTemplate.opsForValue().set(ConstantCommonCode.CACHE_LOGIN_LOCK + username, String.valueOf(expDate.getTime()),ConstantCommonCode.LOCK_MINUTE, TimeUnit.MINUTES);
                ex.setOauth2ErrorCode("您的账号已被冻结15分钟");
            }else {
                stringRedisTemplate.opsForValue().set(ConstantCommonCode.CACHE_LOGIN_LOCK + username, String.valueOf(lockCount), ConstantCommonCode.LOCK_MINUTE, TimeUnit.MINUTES);
                ex.setOauth2ErrorCode("用户密码校验错误，再输错"+(5 - lockCount)+"次该用户将被锁定15分钟");
            }
        }catch (Exception exception){
            ex.setOauth2ErrorCode(exception.getMessage());
        }
        return ex;
    }
}
