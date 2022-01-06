package com.cms.auth.config.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;

/**
 * @author ydf Created by 2022/1/6 11:33
 */
public class OAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception>  {

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        System.out.println("OAuth2请求异常处理->>>");
        // Try to extract a SpringSecurityException from the stacktrace
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        // 异常栈获取OAuth2Exception异常
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }
        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
                causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        ase = (AccessDeniedException) throwableAnalyzer
                .getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase instanceof AccessDeniedException) {
            return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
        }

        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(
                HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase instanceof HttpRequestMethodNotSupportedException) {
            return handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
        }
        // 不包含上述异常则服务器内部错误
        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        //按自定义格式输出错误码
        JiheOauth2Exception exception = new JiheOauth2Exception(e.getMessage(), e);

        ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(exception, headers,
                HttpStatus.valueOf(status));
        System.out.println("输出错误消息==================");
        System.out.println(response);
        return response;
    }

    @SuppressWarnings("serial")
    private static class ForbiddenException extends OAuth2Exception {

        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "access_denied";
        }

        @Override
        public int getHttpErrorCode() {
            return 403;
        }

    }

    @SuppressWarnings("serial")
    private static class ServerErrorException extends OAuth2Exception {

        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "server_error";
        }

        @Override
        public int getHttpErrorCode() {
            return 500;
        }

    }

    @SuppressWarnings("serial")
    private static class UnauthorizedException extends OAuth2Exception {

        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "unauthorized";
        }

        @Override
        public int getHttpErrorCode() {
            return 401;
        }

    }

    @SuppressWarnings("serial")
    private static class MethodNotAllowed extends OAuth2Exception {

        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "method_not_allowed";
        }

        @Override
        public int getHttpErrorCode() {
            return 405;
        }

    }
}
