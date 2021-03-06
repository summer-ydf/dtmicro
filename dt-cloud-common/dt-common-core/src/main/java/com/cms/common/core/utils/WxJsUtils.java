package com.cms.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author DT辰白 Created by 2022/4/13 10:24
 */
public class WxJsUtils {

    static HttpClientInstance httpClientInstance = HttpClientInstance.getInstance();

    static private Log log = LogFactory.getLog(WxJsUtils.class);

    static String appid = "xxx";
    static String secret = "xxx";
    static String openid = "xxx";
    static String template_id = "xxx";
    static String token = "xxx";

    public WxJsUtils() {
    }

    /**
     * https请求方式: GET https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * 获取Access token
     * @param appid 第三方用户唯一凭证
     * @param secret 第三方用户唯一凭证密钥，即appsecret
     * @return 返回Access token，建议保存到缓存中，默认有效时长两小时（7200s）
     */
    public static JSONObject getBaseToken(String appid, String secret){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        url = String.format(url,appid,secret);
        String result = httpClientInstance.get(url);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        log.info("获取Access token->>>"+result);
        return JSON.parseObject(result);
    }

    /**
     * http请求方式: GET（请使用https协议）
     * https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     * @param access_token 调用接口凭证
     * @return 返回用户信息列表
     */
    public static JSONObject getUsers(String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=";
        url = String.format(url,access_token);
        String result = httpClientInstance.get(url);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        log.debug("获取用户列表->>>"+result);
        return JSON.parseObject(result);
    }

    /**
     * http请求方式: GET https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * 获取用户基本信息
     * @param access_token 调用接口凭证
     * @param openid 普通用户的标识，对当前公众号唯一
     * @return 返回用户信息
     */
    public static JSONObject getUserDetail(String access_token, String openid){
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
        url = String.format(url,access_token,openid);
        String result = httpClientInstance.get(url);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        log.debug("获取用户基本信息->>>"+result);
        return JSON.parseObject(result);
    }

    /**
     * 发送模板消息
     * @param token 调用接口凭证
     * @param template 模板
     * @return 返回
     */
    public static JSONObject sendTemplateMsg(String token, Template template) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
        requestUrl = String.format(requestUrl,token);
        String result = httpClientInstance.postJson(requestUrl,template,false);
        log.info("模板消息返回:"+result);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        return JSON.parseObject(result);
    }

    /**
     * 发送消息测试模板
     * @param token 调用接口凭证
     * @param openid 接收者
     * @return 返回json
     */
    public static JSONObject sendTemplateTest(String token,String templateid,String openid) {
        Template tem = new Template();
        tem.setTemplate_id(templateid);
        tem.setTopcolor("#44b549");
        tem.setTouser(openid);
        tem.setUrl("https://blog.csdn.net/qq_41107231");
        Map<String, Object> paras = new HashMap<>();
        paras.put("topic", new TemplateParam("您的订单正在配送中，请及时查看快递员配送信息", "#2f1e5f"));
        paras.put("user", new TemplateParam("XXX先生", "#173177"));
        paras.put("number", new TemplateParam(UUID.randomUUID().toString().replaceAll("-","").substring(0,12), "#2ab530"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        paras.put("date", new TemplateParam(simpleDateFormat.format(new Date()), "#2ab530"));
        paras.put("remark", new TemplateParam("点击详情可查看您的配送信息", "#173177"));
        tem.setData(paras);
        return WxJsUtils.sendTemplateMsg(token, tem);
    }

    public static class Template {
        // 消息接收方
        private String touser;
        // 模板id
        private String template_id;
        // 模板消息详情链接
        private String url;
        // 消息顶部的颜色
        private String topcolor;
        // 参数列表
        private Map<String,Object> data;

        public String getTouser() {
            return touser;
        }

        public void setTouser(String touser) {
            this.touser = touser;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(Map<String, Object> data) {
            this.data = data;
        }

        public String getTopcolor() {
            return topcolor;
        }

        public void setTopcolor(String topcolor) {
            this.topcolor = topcolor;
        }

    }

    private static class TemplateParam {
        // 参数值
        private String value;
        // 颜色
        private String color;

        public TemplateParam(String name,String value,String color) {
            this.value=value;
            this.color=color;
        }

        public TemplateParam(String value, String color) {
            this.value=value;
            this.color=color;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
