package com.cms.provider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备实体类
 * @author ydf Created by 2022/5/17 17:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pms_device")
@ApiModel(value="系统配置对象")
@EqualsAndHashCode(callSuper = true)
public class DeviceEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "设备名称")
    private String name;
}
