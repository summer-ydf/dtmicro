package com.cms.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/2/18 11:13
 */
@Data
@Builder
@ApiModel(value="菜单元数据")
public class SysMenuMeta implements Serializable {

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型：菜单->>>menu 按钮->>>button")
    private String type;

    @ApiModelProperty(value = "隐藏菜单")
    private Boolean hidden = false;

    @ApiModelProperty(value = "隐藏面包屑")
    private Boolean hiddenBreadcrumb = false;

}
