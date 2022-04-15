package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;

/**
 * @author ydf Created by 2022/4/14 16:25
 */
public interface MqMessageService {

    void saveMqMessage(MqMessageEntity mqMessageEntity);

    ResultUtil<IPage<MqMessageEntity>> pageSearch(SysSearchPage request);

    ResultUtil<?> deleteMessageById(String id);

    ResultUtil<?> deleteBathMessage(String[] ids);
}
