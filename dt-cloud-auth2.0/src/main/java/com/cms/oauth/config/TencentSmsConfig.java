package com.cms.oauth.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云短信服务参数配置
 * @author DT辰白 Created by 2022/5/12 17:19
 */
@Configuration
@RefreshScope
public class TencentSmsConfig {

    /**
     * API相关
     */
    private static final String URL ="sms.tencentcloudapi.com";
    private static final String REGION = "ap-guangzhou";

    /**
     * 账号
     */
    @Value("${tencent.sms.secretId}")
    private String secretId;
    @Value("${tencent.sms.secretKey}")
    private String secretKey;

    @Bean
    public SmsClient smsClient() {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(secretId, secretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(URL);
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化 SMS 的 client 对象
        return new SmsClient(cred, REGION, clientProfile);
    }
}
