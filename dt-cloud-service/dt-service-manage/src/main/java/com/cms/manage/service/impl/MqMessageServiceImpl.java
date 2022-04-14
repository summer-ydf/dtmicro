package com.cms.manage.service.impl;

import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.service.MqMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author ydf Created by 2022/4/14 16:25
 */
@Service
public class MqMessageServiceImpl implements MqMessageService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResultUtil<SysMqMessageVoEntity> saveMqMessage(MqMessageEntity mqMessageEntity) {
        MqMessageEntity save = mongoTemplate.save(mqMessageEntity);
        if (!ObjectUtils.isEmpty(save)) {
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }
}
