package com.cms.auth.config.exception;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ydf Created by 2022/1/6 15:02
 */
public class TokenAuthenticationFailureHandler implements OAuth2AuthenticationFailureHandler {
    @Override
    public IccOAuth2Exception onAuthenticationFailure(OAuth2Exception oAuth2Exception) {
        IccOAuth2Exception ex = new IccOAuth2Exception(oAuth2Exception.getMessage(), oAuth2Exception);
        //ex.setHttpErrorCode(oAuth2Exception.getHttpErrorCode());
        ex.setOauth2ErrorCode("用户密码校验错误，用户已被锁定15分钟。");
        System.out.println("密码校验错误->>>"+oAuth2Exception);
//        try{
//            HttpServletRequest request= CoreWebUtils.currentRequest();
//            String login_key = (String) request.getAttribute(SecurityClaimsUser.class.getName()+".login_key");
//            if(StringUtils.isEmpty(login_key)){
//                ex.setOauth2ErrorCode(oAuth2Exception.getOAuth2ErrorCode());
//                return ex;
//            }
//            long lock_count = (long) request.getAttribute(SecurityClaimsUser.class.getName()+".lock_count");
//            lock_count=lock_count+1;
//            if(lock_count==5){
//                Date exp_date= DateUtils.addMinutes(new Date(),login_lock_time);
//                stringRedisTemplate.opsForValue().set(login_key,String.valueOf(exp_date.getTime()),login_lock_time, TimeUnit.MINUTES);
//                ex.setOauth2ErrorCode("用户密码校验错误，用户已被锁定15分钟。");
//            }else {
//                stringRedisTemplate.opsForValue().set(login_key,String.valueOf(lock_count),login_lock_time, TimeUnit.MINUTES);
//                ex.setOauth2ErrorCode("用户密码校验错误，再输错"+(5-lock_count)+"次该用户将被锁定15分钟。");
//            }
//        }catch (Exception exception){
//            ex.setOauth2ErrorCode(exception.getMessage());
//        }
        return ex;
    }
}
