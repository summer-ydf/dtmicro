package com.cms.oauth.service;

/**
 * @author ydf Created by 2022/5/12 17:52
 */
public interface TencentSmsService {

    /**
     * 发送短信的验证码
     * @param phone 手机号
     * @return 返回
     */
    String sendSms(String phone);

    /**
     * 验证验证码
     * @param phone 手机号
     * @param code 验证码
     * @return 返回
     */
    Boolean validationCode(String phone, String code);
}
