package com.cms.manage.pattern.strategy;

import com.alibaba.fastjson.JSONObject;
import com.cms.common.core.utils.WxJsUtils;
import com.cms.common.jdbc.config.IdGenerator;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.entity.WxMessageEntity;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.utils.ConfigPropertyUtils;
import com.cms.manage.vo.WxMessageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.cms.common.tool.constant.ConstantCode.*;

/**
 * @author DT辰白
 * @date 2022/5/22 19:11
 */
@Service("wechatSendMessage")
public class WechatSendMessage implements MessageStrategyService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysOperatorService sysOperatorService;

    @Override
    public ResultUtil<?> sendMessage(WxMessageRequest wxMessageRequest) {
        String enabled = ConfigPropertyUtils.getConfigProperty("wx.enabled");
        String appid = ConfigPropertyUtils.getConfigProperty("wx.appid");
        String secret = ConfigPropertyUtils.getConfigProperty("wx.secret");
        String templateid = ConfigPropertyUtils.getConfigProperty("wx.templateid");
        if (enabled.equals(STR_ZERO)) {
            return ResultUtil.error("公众号消息推送未启用");
        }
        if (StringUtils.isBlank(appid) || StringUtils.isBlank(secret) || StringUtils.isBlank(templateid)) {
            return ResultUtil.error("公众号消息参数未配置");
        }
        // 循环推送信息
        for (Long id : wxMessageRequest.getReceiverIds()) {
            String openid = null;
            SysOperatorEntity operatorEntity = sysOperatorService.getById(id);
            if (StringUtils.isNotBlank(operatorEntity.getOpenid())) {
                openid = operatorEntity.getOpenid();
            }
            if (StringUtils.isBlank(openid)) {
                continue;
            }
            wechatSend(appid, secret, templateid, operatorEntity);
        }
        return ResultUtil.success();
    }

    private void wechatSend(String appid, String secret, String templateid, SysOperatorEntity operatorEntity) {
        // 获取token
        String token = stringRedisTemplate.opsForValue().get(WECHAT_TOKEN_KEY + operatorEntity.getOpenid());
        if (null == token) {
            JSONObject baseToken = WxJsUtils.getBaseToken(appid, secret);
            assert baseToken != null;
            token = baseToken.getString("access_token");
            stringRedisTemplate.opsForValue().set(WECHAT_TOKEN_KEY + operatorEntity.getOpenid(),token,7200L, TimeUnit.SECONDS);
        }
        // 发送消息
        JSONObject jsonObject = WxJsUtils.sendTemplateTest(token, templateid, operatorEntity.getOpenid());
        String errcode = jsonObject.getString("errcode");
        // 存储发送消息日志
        WxMessageEntity message = new WxMessageEntity();
        message.setId(String.valueOf(IdGenerator.generateId()));
        message.setCategory(STR_ONE);
        message.setReceiverId(String.valueOf(operatorEntity.getId()));
        message.setReceiverName(operatorEntity.getName());
        message.setReceiverOpenid(operatorEntity.getOpenid());
        message.setSendData(jsonObject.toJSONString());
        message.setBackData(jsonObject.toJSONString());
        message.setTempId(templateid);
        message.setSendDate(new Date());
        message.setStatus(errcode.equals(STR_ZERO) ? 1 : 2);
        mongoTemplate.save(message);
        SysCmsUtils.log.info("插入MongoDB日志信息，当前线程[{}]",Thread.currentThread().getName());
    }
}
