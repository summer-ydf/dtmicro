package com.cms.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.core.utils.WxJsUtils;
import com.cms.common.jdbc.config.IdGenerator;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.entity.WxMessageEntity;
import com.cms.manage.service.MessageService;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.utils.ConfigPropertyUtils;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.cms.common.tool.constant.ConstantCode.*;

/**
 * @author ydf Created by 2022/4/14 16:25
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysOperatorService sysOperatorService;

    @Async("sysTaskExecutor")
    @Override
    public void saveMqMessage(MqMessageEntity mqMessageEntity) {
        // 程序异常，mongodb事务不生效，需要开启事务
        // mongoDB单个实例不支持事务，副本集才支持事务
        mongoTemplate.save(mqMessageEntity);
        SysCmsUtils.log.info("插入MongoDB日志信息，当前线程[{}]",Thread.currentThread().getName());
    }

    @Override
    public ResultUtil<IPage<MqMessageEntity>> pageMqSearch(SysSearchPage request) {
        // 创建查询对象
        Query query = new Query();
        // 获取页码参数
        Integer pageNum = request.getCurrent();
        Integer pageSize = request.getSize();
        if (null != request.getStatus()) {
            query.addCriteria(Criteria.where("publish_status").is(request.getStatus()));
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.addCriteria(Criteria.where("message_id").is(request.getKeyword()));
        }
        // 查询总记录数
        int count = (int) mongoTemplate.count(query, MqMessageEntity.class);
        // 按照创建时间倒序
        query.with(Sort.by(Sort.Order.desc("publish_date")));
        // 设置起始数
        query.skip((long) (pageNum - 1) * pageSize);
        // 设置查询条数
        query.limit(pageSize);
        // 查询当前页数据集合
        List<MqMessageEntity> taskList = mongoTemplate.find(query, MqMessageEntity.class);
        // 创建分页实体对象
        Page<MqMessageEntity> page = new Page<>();
        //添加每页的集合、数据总条数、总页数
        page.setRecords(taskList);
        page.setTotal(count);
        page.setSize(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        return ResultUtil.success(page);
    }

    @Override
    public ResultUtil<?> deleteMqMessageById(String id) {
        MqMessageEntity mqMessageEntity = mongoTemplate.findById(id, MqMessageEntity.class);
        System.out.println("查询到的数据:"+mqMessageEntity);
        if (!ObjectUtils.isEmpty(mqMessageEntity)) {
            mongoTemplate.remove(mqMessageEntity);
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil<?> deleteBathMqMessage(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        DeleteResult result = mongoTemplate.remove(Query.query(Criteria.where("id").in(idList)), MqMessageEntity.class);
        return ResultUtil.success(result.getDeletedCount());
    }

    @Override
    public ResultUtil<IPage<WxMessageEntity>> pageWxSearch(SysSearchPage request) {
        // 创建查询对象
        Query query = new Query();
        // 获取页码参数
        Integer pageNum = request.getCurrent();
        Integer pageSize = request.getSize();
        if (null != request.getStatus()) {
            query.addCriteria(Criteria.where("status").is(request.getStatus()));
        }
        if (StringUtils.isNotBlank(request.getCategory())) {
            query.addCriteria(Criteria.where("category").is(request.getCategory()));
        }
        // 查询总记录数
        int count = (int) mongoTemplate.count(query, WxMessageEntity.class);
        // 按照创建时间倒序
        query.with(Sort.by(Sort.Order.desc("publish_date")));
        // 设置起始数
        query.skip((long) (pageNum - 1) * pageSize);
        // 设置查询条数
        query.limit(pageSize);
        // 查询当前页数据集合
        List<WxMessageEntity> taskList = mongoTemplate.find(query, WxMessageEntity.class);
        // 创建分页实体对象
        Page<WxMessageEntity> page = new Page<>();
        //添加每页的集合、数据总条数、总页数
        page.setRecords(taskList);
        page.setTotal(count);
        page.setSize(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        return ResultUtil.success(page);
    }

    @Override
    public ResultUtil<?> deleteWxMessageById(String id) {
        WxMessageEntity wxMessageEntity = mongoTemplate.findById(id, WxMessageEntity.class);
        SysCmsUtils.log.info("查询到Mongodb的数据:{}",wxMessageEntity);
        if (!ObjectUtils.isEmpty(wxMessageEntity)) {
            mongoTemplate.remove(wxMessageEntity);
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil<?> deleteBathWxMessage(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        DeleteResult result = mongoTemplate.remove(Query.query(Criteria.where("id").in(idList)), WxMessageEntity.class);
        return ResultUtil.success(result.getDeletedCount());
    }

    @Override
    public ResultUtil<?> wxSendMessage(List<Long> receiverIds) {
        // TODO 发送消息数据量过大时，可以将消息先放入消息队列中，单独的消息服务来处理
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
        for (Long id : receiverIds) {
            String openid = null;
            SysOperatorEntity operatorEntity = sysOperatorService.getById(id);
            if (StringUtils.isNotBlank(operatorEntity.getOpenid())) {
                openid = operatorEntity.getOpenid();
            }
            if (StringUtils.isBlank(openid)) {
                continue;
            }
            // 获取token
            String token = stringRedisTemplate.opsForValue().get(WECHAT_TOKEN_KEY + openid);
            if (null == token) {
                JSONObject baseToken = WxJsUtils.getBaseToken(appid, secret);
                assert baseToken != null;
                token = baseToken.getString("access_token");
                stringRedisTemplate.opsForValue().set(WECHAT_TOKEN_KEY + openid,token,7200L, TimeUnit.SECONDS);
            }
            // 发送消息
            JSONObject jsonObject = WxJsUtils.sendTemplateTest(token, templateid, openid);
            String errcode = jsonObject.getString("errcode");
            // 存储发送消息日志
            WxMessageEntity message = new WxMessageEntity();
            message.setId(String.valueOf(IdGenerator.generateId()));
            message.setCategory(STR_ONE);
            message.setReceiverId(String.valueOf(id));
            message.setReceiverName(operatorEntity.getName());
            message.setReceiverOpenid(openid);
            message.setSendData(jsonObject.toJSONString());
            message.setBackData(jsonObject.toJSONString());
            message.setTempId(templateid);
            message.setSendDate(new Date());
            message.setStatus(errcode.equals(STR_ZERO) ? 1 : 2);
            mongoTemplate.save(message);
            SysCmsUtils.log.info("插入MongoDB日志信息，当前线程[{}]",Thread.currentThread().getName());
        }
        return ResultUtil.success();
    }
}
