package com.cms.common.tool.enums;

import cn.hutool.core.util.ObjectUtil;

import java.util.EnumSet;

/**
 * @author DT辰白
 * @date 2022/5/22 21:09
 */
public enum MessageEnum {

    WECHAT_MESSAGE("1", "wechatSendMessage", "微信公众号"),
    QY_MESSAGE("2", "qySendMessage", "企业微信");

    private String category;

    private String serviceName;

    private String desc;

    MessageEnum(String category, String serviceName, String desc) {
        this.category = category;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    public static MessageEnum getValue(String value) {
        // 获取类型下的所有枚举
        EnumSet<MessageEnum> allEnums = EnumSet.allOf(MessageEnum.class);
        return allEnums.stream()
                .filter(e -> ObjectUtil.equal(e.getCategory(), value))
                .findFirst()
                .orElse(null);
    }

    public String getCategory() {
        return category;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDesc() {
        return desc;
    }
}
