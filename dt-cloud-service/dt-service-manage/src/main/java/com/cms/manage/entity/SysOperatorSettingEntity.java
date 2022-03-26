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
 * @author DT
 * @date 2022/3/26 19:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_operator_setting")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="用户自定义系统设置")
public class SysOperatorSettingEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "常用应用")
    private String app;
}
