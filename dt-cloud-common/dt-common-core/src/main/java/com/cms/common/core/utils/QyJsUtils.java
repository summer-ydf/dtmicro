package com.cms.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DT辰白 Created by 2022/4/15 14:11
 */
public class QyJsUtils {

    static HttpClientInstance httpClientInstance = HttpClientInstance.getInstance();

    static private Log log = LogFactory.getLog(QyJsUtils.class);

    /**
     * 企业ID
     */
    static String corpid = "ww5aad0e92512e0e74";
    /**
     * 应用ID
     */
    static String agentid = "1000002";
    /**
     * 应用的凭证密钥
     */
    static String corpsecret = "zK6-H0X1IPL6yAQNNdUUuoGM9fbgJ5CSatfSTmz70JE";
    /**
     * Token令牌
     */
    static String token = "zoNBzRldopeR_xBw63t_gCfMQMl2IMoXQ6nG5lg5vyuYgPhme2GhG_lk9vocRcC5RrLZDqx53TBBYYmA2th-4yzw0AQn5hbAyWs5QpawetCwX5ehpvzwABAnTD6R9aNF-j5-MtuSirOszgMrVTPN0f6XDIu-YjaeS61U63GddL0ePYOSbRxUzgEweE0N96CFtGJz3a6jomt8E0qyFbYHkA";

    public QyJsUtils() {
    }

    /**
     * 获取token令牌
     * @param corpid 企业ID
     * @param corpsecret 应用的凭证密钥
     * @return 返回令牌
     */
    public static JSONObject getToken(String corpid, String corpsecret) {
        String openIdUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
        String result = httpClientInstance.get(String.format(openIdUrl, corpid, corpsecret));
        if(StringUtils.isEmpty(result)){
            return null;
        }
        log.info("获取Access token->>>"+result);
        return JSON.parseObject(result);
    }

    /**
     * 企业消息推送
     * @param access_token token令牌
     * @param agentid 应用ID
     * @param touser 消息接收者
     * @param content 发送内容
     * @return 返回
     */
    public static JSONObject send_messge(String access_token,String agentid,String touser,String content) {
        String openIdUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
        Map<String,Object> params = new HashMap<>();
        params.put("touser",touser);
        params.put("msgtype","text");
        params.put("agentid",agentid);
        params.put("safe","0");
        params.put("enable_id_trans","0");
        params.put("enable_duplicate_check","0");
        params.put("duplicate_check_interval","1800");
        Map<String,Object> text = new HashMap<>();
        text.put("content",content);
        params.put("text",text);
        String format = String.format(openIdUrl, access_token);
        String postJson = httpClientInstance.postJson(format, params, false);
        if(StringUtils.isEmpty(postJson)){
            return null;
        }
        return JSON.parseObject(postJson);
    }

    /**
     * 手机号获取userid
     * @param access_token 调用接口凭证
     * @param mobile 用户在企业微信通讯录中的手机号码。长度为5~32个字节
     * @return 返回用户信息
     */
    public static JSONObject getUserId(String access_token,String mobile) {
        String openIdUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserid?access_token=%s";
        Map<String,Object> params = new HashMap<>();
        params.put("mobile",mobile);
        String format = String.format(openIdUrl, access_token);
        String postJson = httpClientInstance.postJson(format, params, false);
        if(StringUtils.isEmpty(postJson)){
            return null;
        }
        log.info("手机号获取userid->>>"+postJson);
        return JSON.parseObject(postJson);
    }


    public static void main(String[] args) {
//        JSONObject token = getToken(corpid, corpsecret);
//        send_messge(token,agentid,"yandongfa","你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。");
        getUserId(token,"13550001448");
    }


}
