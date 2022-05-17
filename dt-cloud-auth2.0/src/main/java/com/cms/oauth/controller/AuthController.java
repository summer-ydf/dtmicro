package com.cms.oauth.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cms.common.core.utils.HttpClientInstance;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * Gitee第三方登录
     * @return 返回登录页面
     */
    @RequestMapping(value = "/anonymous/giteeLogin")
    public String login() {
        // https://www.cnblogs.com/lzj123/p/10279007.html
        // https://gitee.com/UnityAlvin/gulimall/commit/222302f1e34f78d8f0d5045363857fb693604608
        System.out.println("返回自定义登录页面");
        return "gitee/login";
    }

    /**
     * Gitee登录回调
     * @param code 授权码
     * @return 返回处理成功页面
     */
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
        JSONObject baseJson = JSON.parseObject(post);
        System.out.println("获取accesstoken："+baseJson);
        // 获取用户基础信息
        String accessToken = baseJson.getString("access_token");
        String url = "https://gitee.com/api/v5/user?access_token=%s";
        String format_url = String.format(url, accessToken);
        String get = httpClientInstance.get(format_url);
        JSONObject infoJson = JSON.parseObject(get);
        System.out.println("获取用户基础信息："+infoJson);
        if (StringUtils.isNotBlank(code)) {
            // 封装登录请求参数
            return "gitee/success";
        }
        return "gitee/login";
    }

    /**
     * 授权码模式自定义登录页面
     * @return 返回登录页面
     */
    @RequestMapping(value = "/anonymous/cmsLogin")
    public String cmsLogin() {
        // https://www.cnblogs.com/lzj123/p/10279007.html
        // 1、访问认证服务器获取 code
        // GET请求 http://localhost:8086/oauth/authorize?client_id=cms-web&response_type=code&scope=all&redirect_uri=http://www.baidu.com
        // 2、认证服务器会重定向到 login 页面让用户进行登录授权，成功之后认证服务器重定向到指定的url
        // 3、在重定向的 url 后面会带上授权码 code，此时客户端则可以拿这个授权码 code 去换取 token
        // POST请求 http://localhost:8086/oauth/token?client_secret=dt$pwd123&scope=all&client_id=cms-web&redirect_uri=http://www.baidu.com&code=MmdJcX&client_secret=dt$pwd123&grant_type=authorization_code
        System.out.println("返回自定义登录页面");
        return "login";
    }

}
