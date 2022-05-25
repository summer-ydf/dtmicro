package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.MqMessageEntity;
import com.cms.manage.entity.WxMessageEntity;
import com.cms.manage.vo.WxMessageRequest;

import java.util.List;

/**
 * @author DT辰白 Created by 2022/4/14 16:25
 */
public interface MessageService {

    void saveMqMessage(MqMessageEntity mqMessageEntity);

    ResultUtil<IPage<MqMessageEntity>> pageMqSearch(SysSearchPage request);

    ResultUtil<?> deleteMqMessageById(String id);

    ResultUtil<?> deleteBathMqMessage(String[] ids);

    ResultUtil<IPage<WxMessageEntity>> pageWxSearch(SysSearchPage request);

    ResultUtil<?> deleteWxMessageById(String id);

    ResultUtil<?> deleteBathWxMessage(String[] ids);

    ResultUtil<?> wxSendMessage(WxMessageRequest wxMessageRequest);
}
