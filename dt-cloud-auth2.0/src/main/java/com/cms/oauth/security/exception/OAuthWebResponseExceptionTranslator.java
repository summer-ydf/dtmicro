package com.cms.oauth.security.exception;

import com.cms.common.tool.result.ResultEnum;
import com.cms.common.tool.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * OAuth2通用异常处理
 * @author DT辰白 Created by 2022/4/28 10:29
 */
@Slf4j
public class OAuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<ResultUtil> translate(Exception e) {

        ResultUtil<Object> resultUtil = handleOAuth2Exception(e);

        return new ResponseEntity<>(resultUtil, HttpStatus.UNAUTHORIZED);
    }

    private ResultUtil<Object> handleOAuth2Exception(Exception e) {
        log.info("OAuth2单点登录全局异常处理【{}】",e.getMessage());
        ResultEnum resultEnum = null;
        if(e instanceof UnsupportedGrantTypeException) {
            log.error("不支持的认证模式========================");
            resultEnum = ResultEnum.OAUTH2_UNSUPPORTED_GRANT_TYPE;
        }else if (e instanceof InvalidScopeException) {
            log.error("非授权范围========================");
            resultEnum = ResultEnum.OAUTH2_INVALID_SCOPE_ERROR;
        }else if (e instanceof UsernameNotFoundException || e instanceof ParameterAuthenticationException) {
            log.error("参数异常========================");
            String message = e.getMessage();
            resultEnum = ResultEnum.OAUTH2_PARAMETER_ERROR;
            resultEnum.setMessage(message);
        }else if(e instanceof InvalidGrantException) {
            log.error("认证失败，密码错误");
            resultEnum = ResultEnum.OAUTH2_PARAMETER_ERROR;
            resultEnum.setMessage("密码错误");
        }else {
            resultEnum = ResultEnum.OAUTH2_UNAUTHORIZED_CLIENT_ERROR;
        }
        return ResultUtil.failed(resultEnum);
    }
}
