package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cms.common.jdbc.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DT
 * @date 2022/4/12 21:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_mq_message")
@ApiModel(value="MQ消息实体对象")
@EqualsAndHashCode(callSuper = true)
public class SysMqMessageEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "消息ID")
    private String messageId;

    @ApiModelProperty(value = "主题")
    private String title;

    @ApiModelProperty(value = "成功/失败消息")
    private String messaage;

    @ApiModelProperty(value = "发布时间")
    private Date publishDate;

    @ApiModelProperty(value = "发布状态：1成功 2失败")
    private Integer publishStatus;
}
