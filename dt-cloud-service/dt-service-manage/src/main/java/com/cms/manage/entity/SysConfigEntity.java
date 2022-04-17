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

/**
 * @author ydf Created by 2022/4/15 17:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_config")
@ApiModel(value="系统配置对象")
@EqualsAndHashCode(callSuper = true)
public class SysConfigEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "配置key")
    private String k;

    @ApiModelProperty(value = "配置value")
    private String v;

    @ApiModelProperty(value = "配置类型")
    private String type;

}
