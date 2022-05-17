package com.cms.oauth.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cms.common.core.utils.HttpClientInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DT
 * @date 2022/5/16 22:55
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/anonymous/myLogin")
    public String login() {
        // https://www.cnblogs.com/lzj123/p/10279007.html
        // https://gitee.com/UnityAlvin/gulimall/commit/222302f1e34f78d8f0d5045363857fb693604608
        System.out.println("返回自定义登录页面");
        return "login";
    }

    @RequestMapping(value = "anonymous/authcode")
    public String authcode(@RequestParam("code") String code) {
        System.out.println("获取到授权码："+code);
        // 准备请求参数
        Map<String,Object> params = new HashMap<>();
        params.put("client_id","0b9b675ca37e6018dc733e6a925ed12300050c2b52a5b41094c14f8eb15ec44e");
        params.put("redirect_uri","http://127.0.0.1:8086/anonymous/authcode");
        params.put("client_secret","751147dad27f81df05025450e21c8dcbc80b1c5f4a4023cf31c1e1dcb6d5db7b");
        params.put("code",code);
        params.put("grant_type","authorization_code");
        // 获取accesstoken
        HttpClientInstance httpClientInstance = HttpClientInstance.getInstance();
       String post = httpClientInstance.post("https://gitee.com/oauth/token", params);
       JSONObject jsonObject = JSON.parseObject(post);
        System.out.println("返回用户信息===========");
        System.out.println(jsonObject);
        if (StringUtils.isNotBlank(code)) {
            // 封装登录请求参数
            return "success";
        }
        return "login";
    }
}
