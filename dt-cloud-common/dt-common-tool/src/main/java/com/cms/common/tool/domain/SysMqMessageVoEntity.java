package com.cms.common.tool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DT
 * @date 2022/4/12 21:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMqMessageVoEntity implements Serializable {

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 主题
     */
    private String title;

    /**
     * 成功/失败消息
     */
    private String message;

    /**
     * 发布时间
     */
    private Date publishDate;

    /**
     * 发布状态：1成功 2失败
     */
    private Integer publishStatus;
}
