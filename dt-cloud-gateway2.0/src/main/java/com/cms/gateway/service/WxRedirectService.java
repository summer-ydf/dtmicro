package com.cms.gateway.service;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author DT辰白 Created by 2022/4/2 9:51
 */
@CommonsLog
@Component
public class WxRedirectService {

    public Object redirect(String cookieValue, String code, String auth, String url, String viewName, Model model, Map<String, Object> props, ServerWebExchange exchange) {
        // 获取HTTP请求携带参数
        MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
        String qystate = ObjectUtils.toString(params.getFirst("qystate"));
        String sno = ObjectUtils.toString(params.getFirst("sno"));
        // 向客户端写入cookie
        ResponseCookie responseCookie = ResponseCookie.from("ufs_openId", sno).maxAge(Integer.MAX_VALUE).build();
        exchange.getResponse().addCookie(responseCookie);
        // 传入sno参数
        model.addAttribute("openId", sno);
        return Mono.create(monoSink -> monoSink.success(viewName));
    }
}
