package com.cms.manage.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ydf Created by 2022/4/14 16:17
 */
@Data
@Document(collation = "mq_message_record")
public class MqMessageEntity implements Serializable {
    @Id
    private String id;

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
    @Indexed
    private Integer publishStatus;
}
