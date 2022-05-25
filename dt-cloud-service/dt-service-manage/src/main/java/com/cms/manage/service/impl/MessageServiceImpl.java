package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.entity.WxMessageEntity;
import com.cms.manage.pattern.strategy.MessageStrategyContext;
import com.cms.manage.pattern.strategy.MessageStrategyService;
import com.cms.manage.service.MessageService;
import com.cms.manage.vo.WxMessageRequest;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author DT辰白 Created by 2022/4/14 16:25
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MessageStrategyContext messageStrategyContext;

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
    public ResultUtil<?> wxSendMessage(WxMessageRequest wxMessageRequest) {
        MessageStrategyService service = messageStrategyContext.getService(wxMessageRequest.getCategory());
        return service.sendMessage(wxMessageRequest);
    }
}
