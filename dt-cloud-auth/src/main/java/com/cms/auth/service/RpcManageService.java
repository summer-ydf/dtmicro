package com.cms.auth.service;

import com.api.manage.feign.MessageFeignClientService;
import com.cms.common.tool.domain.SysMqMessageVoEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2022/4/13 17:42
 */
@Service
public class RpcManageService {

    @Autowired
    private MessageFeignClientService messageFeignClientService;

    public void saveMqMessageInfo(SysMqMessageVoEntity mqMessageVoEntity) {
        ResultUtil<SysMqMessageVoEntity> sysMqMessageVoEntityResultUtil = messageFeignClientService.saveMqMessage(mqMessageVoEntity);
        SysCmsUtils.log.info("远程调用回调结果->>>" + sysMqMessageVoEntityResultUtil);
    }
}
