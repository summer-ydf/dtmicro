package com.cms.manage.pattern.strategy;

import com.alibaba.fastjson.JSONObject;
import com.cms.common.core.utils.QyJsUtils;
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
 * @author DT
 * @date 2022/5/22 19:11
 */
@Service("qySendMessage")
public class QyWechatSendMessage implements MessageStrategyService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysOperatorService sysOperatorService;

    @Override
    public ResultUtil<?> sendMessage(WxMessageRequest wxMessageRequest) {
        String enabled = ConfigPropertyUtils.getConfigProperty("qy.enabled");
        String corpid = ConfigPropertyUtils.getConfigProperty("qy.corpid");
        String agentid = ConfigPropertyUtils.getConfigProperty("qy.agentid");
        String corpsecret = ConfigPropertyUtils.getConfigProperty("qy.corpsecret");
        if (enabled.equals(STR_ZERO)) {
            return ResultUtil.error("企业微信消息推送未启用");
        }
        if (StringUtils.isBlank(corpid) || StringUtils.isBlank(agentid) || StringUtils.isBlank(corpsecret)) {
            return ResultUtil.error("企业微信消息参数未配置");
        }
        for (Long id : wxMessageRequest.getReceiverIds()) {
            SysOperatorEntity operatorEntity = sysOperatorService.getById(id);
            if (StringUtils.isBlank(operatorEntity.getName())) {
                continue;
            }
            qyWechatSend(corpid, corpsecret, agentid, operatorEntity);
        }
        return ResultUtil.success();
    }

    private void qyWechatSend(String corpid,String corpsecret,String agentid,SysOperatorEntity operatorEntity) {
        // 获取token
        String token = stringRedisTemplate.opsForValue().get(QYWECHAT_TOKEN_KEY + operatorEntity.getName());
        if (null == token) {
            JSONObject baseToken = QyJsUtils.getToken(corpid, corpsecret);
            assert baseToken != null;
            token = baseToken.getString("access_token");
            stringRedisTemplate.opsForValue().set(QYWECHAT_TOKEN_KEY + operatorEntity.getName(),token,7200L, TimeUnit.SECONDS);
        }
        // 发送消息
        String content = "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。";
        JSONObject jsonObject = QyJsUtils.send_messge(token, agentid, operatorEntity.getName(),content);
        String errcode = jsonObject.getString("errcode");
        // 存储发送消息日志
        WxMessageEntity message = new WxMessageEntity();
        message.setId(String.valueOf(IdGenerator.generateId()));
        message.setCategory(STR_TWO);
        message.setReceiverId(String.valueOf(operatorEntity.getId()));
        message.setReceiverName(operatorEntity.getName());
        message.setReceiverOpenid(operatorEntity.getName());
        message.setSendData(jsonObject.toJSONString());
        message.setBackData(jsonObject.toJSONString());
        message.setSendDate(new Date());
        message.setStatus(errcode.equals(STR_ZERO) ? 1 : 2);
        mongoTemplate.save(message);
        SysCmsUtils.log.info("插入MongoDB日志信息，当前线程[{}]",Thread.currentThread().getName());
    }
}
