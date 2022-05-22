package com.cms.manage.pattern.strategy;

import com.cms.common.tool.enums.MessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DT
 * @date 2022/5/22 19:14
 */
@Component
public class MessageStrategyContext {

    @Autowired
    private final Map<String, MessageStrategyService> strategyMap = new ConcurrentHashMap<>(3);

    public MessageStrategyService getService(String category) {
        MessageEnum messageEnum = MessageEnum.getValue(category);
        return strategyMap.get(messageEnum.getServiceName());
    }
}
