package com.cms.manage.service;

import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;

/**
 * @author ydf Created by 2022/4/14 16:25
 */
public interface MqMessageService {

    ResultUtil<SysMqMessageVoEntity> saveMqMessage(MqMessageEntity mqMessageEntity);
}
