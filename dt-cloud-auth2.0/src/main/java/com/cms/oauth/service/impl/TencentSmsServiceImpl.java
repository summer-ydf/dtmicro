package com.cms.oauth.service.impl;


import com.cms.oauth.service.TencentSmsService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ydf Created by 2022/5/12 17:53
 */
@Slf4j
@Service
public class TencentSmsServiceImpl implements TencentSmsService {

    /**
     * 短信验证码长度
     */
    private final Integer LENGTH = 6;

    @Autowired
    private SmsClient smsClient;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${tencent.sms.account.sms_sdk_app_id}")
    private String SMS_SDK_APP_ID;
    @Value("${tencent.sms.account.template_id}")
    private String TEMPLATE_ID;
    @Value("${tencent.sms.account.sign_name}")
    private String SIGN_NAME;

    @Override
    public String sendSms(String phone) {
        String[] phoneNumbers = {"+86" + phone};
        //生成随机验证码
        final String code = generateCode();
        //加入数组
        String[] templateParams = {code};
        //实例请求，组装参数
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setSmsSdkAppid(SMS_SDK_APP_ID);
        sendSmsRequest.setTemplateID(TEMPLATE_ID);
        sendSmsRequest.setSign(SIGN_NAME);
        //发送的手机号
        sendSmsRequest.setPhoneNumberSet(phoneNumbers);
        //发送的内容（验证码）
        sendSmsRequest.setTemplateParamSet(templateParams);
        try {
            //发送
            final SendSmsResponse sendSmsResponse = smsClient.SendSms(sendSmsRequest);
            log.info("短信发送成功：{}", sendSmsResponse.toString());
            //加入缓存
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return "OK";
        } catch (TencentCloudSDKException e) {
            log.error("发送失败，或者剩余短信数量不足", e);
        }
        return "发送失败，或者剩余短信数量不足";
    }

    @Override
    public Boolean validationCode(String phone, String code) {
        final String data = (String) redisTemplate.opsForValue().get(phone);
        if (code.equals(data)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 生成随机的验证码
     *
     * @return
     */
    public String generateCode() {
        return RandomStringUtils.randomNumeric(LENGTH);
    }
}
