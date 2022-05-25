package com.cms.manage.pattern.strategy;

import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.vo.WxMessageRequest;

/**
 * @author DT辰白
 * @date 2022/5/22 19:10
 */
@FunctionalInterface
public interface MessageStrategyService {

    ResultUtil<?> sendMessage(WxMessageRequest wxMessageRequest);
}
