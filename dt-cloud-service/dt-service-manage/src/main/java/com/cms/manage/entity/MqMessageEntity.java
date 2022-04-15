package com.cms.manage.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ydf Created by 2022/4/14 16:17
 */
@Data
@Document(collection = "mq_message_record")
public class MqMessageEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 消息ID
     */
    @Indexed(unique = true)
    @Field("message_id")
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
    @Field("publish_date")
    private Date publishDate;

    /**
     * 发布状态：1成功 2失败
     */
    @Indexed
    @Field("publish_status")
    private Integer publishStatus;
}
