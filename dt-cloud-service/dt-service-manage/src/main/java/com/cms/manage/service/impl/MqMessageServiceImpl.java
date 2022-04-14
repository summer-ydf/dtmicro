package com.cms.manage.service.impl;

import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.service.MqMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ydf Created by 2022/4/14 16:25
 */
@Service
public class MqMessageServiceImpl implements MqMessageService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Async("sysTaskExecutor")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMqMessage(MqMessageEntity mqMessageEntity) {
        System.out.println("添加记录："+mqMessageEntity);
        mongoTemplate.save(mqMessageEntity);
    }
}
