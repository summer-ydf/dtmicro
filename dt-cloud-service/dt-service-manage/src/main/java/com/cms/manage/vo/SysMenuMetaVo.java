package com.cms.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author ydf Created by 2022/2/17 16:43
 */
@Data
@Builder
@ApiModel(value="菜单元数据传输对象")
public class SysMenuMetaVo {

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型：菜单->>>menu 按钮->>>button")
    private String type;
}
