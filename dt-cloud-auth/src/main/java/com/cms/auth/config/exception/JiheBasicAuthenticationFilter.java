package com.cms.auth.config.exception;

import cn.hutool.http.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * @author ydf Created by 2022/1/6 12:49
 */
@Component
public class JiheBasicAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!request.getRequestURI().equals("/oauth/token")) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] clientDetails = this.isHasClientDetails(request);

        if (clientDetails == null) {
            System.out.println("请求中未包含客户端信息=============");
            return;
        }

        this.handle(request,response,clientDetails,filterChain);


    }

    private void handle(HttpServletRequest request, HttpServletResponse response, String[] clientDetails, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            filterChain.doFilter(request,response);
            return;
        }


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(clientDetails[0], clientDetails[1], authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);


        filterChain.doFilter(request,response);
    }

    // 判断请求头中是否包含client信息，不包含返回false
    private String[] isHasClientDetails(HttpServletRequest request) {

        String[] params = null;

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {

            String basic = header.substring(0, 5);

            if (basic.toLowerCase().contains("basic")) {

                String tmp = header.substring(6);
                String defaultClientDetails = new String(Base64.getDecoder().decode(tmp));

                String[] clientArrays = defaultClientDetails.split(":");

                if (clientArrays.length != 2) {
                    return params;
                } else {
                    params = clientArrays;
                }

            }
        }

        String id = request.getParameter("client_id");
        String secret = request.getParameter("client_secret");

        if (header == null && id != null) {
            params = new String[]{id, secret};
        }


        return params;
    }

}

