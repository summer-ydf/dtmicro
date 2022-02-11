package com.cms.common.log.service;

import com.cms.common.tool.domain.SysOperatorLogVoEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2022/2/11 15:46
 */
@Service
public class AsyncLogService {

    /**
     * 保存系统日志记录
     */
    @Async
    public void save(SysOperatorLogVoEntity sysLog) {
        System.out.println("操作日志====================");
        System.out.println(sysLog);
    }
}
