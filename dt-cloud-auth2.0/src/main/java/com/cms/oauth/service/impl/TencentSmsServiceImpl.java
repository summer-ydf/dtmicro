package com.cms.oauth.service.impl;


import com.cms.common.jdbc.utils.RedisUtils;
import com.cms.oauth.service.TencentSmsService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import static com.cms.common.tool.constant.ConstantCode.PHONE_CODE_KEY;

/**
 * @author DT辰白 Created by 2022/5/12 17:53
 */
@Slf4j
@Service
@RefreshScope
public class TencentSmsServiceImpl implements TencentSmsService {

    private final SmsClient smsClient;
    private final RedisUtils redisUtils;

    @Value("${tencent.sms.sdkAppId}")
    private String sdkAppId;
    @Value("${tencent.sms.templateId}")
    private String templateId;
    @Value("${tencent.sms.signName}")
    private String signName;

    public TencentSmsServiceImpl(SmsClient smsClient, RedisUtils redisUtils) {
        this.smsClient = smsClient;
        this.redisUtils = redisUtils;
    }

    @Override
    public String sendSms(String phone) {
        // 构建目标手机号码
        String[] phoneNumbers = {"+86" + phone};
        // 构建随机验证码
        final String code = generateCode();
        // 构建内容参数
        String[] templateParams = {code,"5"};
        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setSmsSdkAppid(sdkAppId);
        sendSmsRequest.setTemplateID(templateId);
        sendSmsRequest.setSign(signName);
        // 发送的手机号
        sendSmsRequest.setPhoneNumberSet(phoneNumbers);
        // 发送的内容（验证码）
        sendSmsRequest.setTemplateParamSet(templateParams);
        try {
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = smsClient.SendSms(sendSmsRequest);
            SendStatus[] statusSet = resp.getSendStatusSet();
            for (SendStatus status : statusSet) {
                String statusCode = status.getCode();
                if (statusCode.equals("Ok")) {
                    log.info("短信发送成功：{}", SendSmsResponse.toJsonString(resp));
                    // 加入缓存
                    redisUtils.set((PHONE_CODE_KEY + phone).toLowerCase(), code,60 * 5L);
                }
            }
            return "发送成功";
        } catch (TencentCloudSDKException e) {
            log.error("发送失败，或者剩余短信数量不足", e);
        }
        return "发送失败，或者剩余短信数量不足";
    }

    @Override
    public Boolean validationCode(String phone, String code) {
        String redisKey = (PHONE_CODE_KEY + phone).toLowerCase();
        Object redisValue = redisUtils.get(redisKey);
        if (code.equals(redisValue)) {
            // 验证通过，删除缓存
            redisUtils.del(redisKey);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 生成随机的验证码
     */
    public String generateCode() {
        return RandomStringUtils.randomNumeric(6);
    }
}
