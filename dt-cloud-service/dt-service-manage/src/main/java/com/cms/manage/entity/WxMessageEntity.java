package com.cms.manage.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DT辰白 Created by 2022/4/15 17:24
 */
@Data
@Document(collection = "wx_message_record")
public class WxMessageEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 模板ID
     */
    @Field("temp_id")
    private String tempId;

    /**
     * 发送人ID
     */
    @Field("sender_id")
    private String senderId;

    /**
     * 接收人ID
     */
    @Field("receiver_id")
    private String receiverId;

    /**
     * 接收人姓名
     */
    @Field("receiver_name")
    private String receiverName;

    /**
     * 接收人openid
     */
    @Field("receiver_openid")
    private String receiverOpenid;

    /**
     * 状态,0待发送，1成功，2失败
     */
    private Integer status = 0;

    /**
     * 发送结构报文
     */
    @Field("send_data")
    private String sendData;

    /**
     * 返回结构报文
     */
    @Field("back_data")
    private String backData;

    /**
     * 发送时间
     */
    @Field("send_date")
    private Date sendDate;

    /**
     * 发送类型：1微信公众号 2企业微信
     */
    private String category;

}
