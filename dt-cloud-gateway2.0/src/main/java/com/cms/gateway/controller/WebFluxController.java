package com.cms.gateway.controller;

import com.cms.gateway.service.WxRedirectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

/**
 * @author DT辰白 Created by 2022/4/2 10:52
 */
@Controller
public class WebFluxController {

    private final WxRedirectService wxRedirectService;
    private static final Map<String, Object> EMPTY = Collections.emptyMap();

    public WebFluxController(WxRedirectService wxRedirectService) {
        this.wxRedirectService = wxRedirectService;
    }

    private Object redirect(String cookieValue, String code, String url, String viewName, Model model, Map<String, Object> props, ServerWebExchange exchange) {
        return wxRedirectService.redirect(cookieValue, code,null, url, viewName, model, props,exchange);
    }

    /**
     * CookieValue 是SpringMVC中的注解，用来获取Cookie中的值
     * ServerWebExchange 命名为服务网络交换器，存放着重要的请求-响应属性、请求实例和响应实例等等
     * @param cookieValue cookie值
     * @param code 业务代码
     * @param model 传参
     * @param exchange 服务网络交换器，
     * @return 返回视图
     */
    @RequestMapping("/wx_workflow")
    public Object wechat_workflow(@CookieValue(name = "ufs_openId",required = false) String cookieValue, @RequestParam(required = false) String code, final Model model, ServerWebExchange exchange) {
        return redirect(cookieValue,code,"wx_workflow","workflow",model,EMPTY,exchange);
    }

    @GetMapping("/index")
    @ResponseBody
    public Mono<String> index() {
        return Mono.just("Welcome to reactive world ~");
    }
}
