package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cms.common.jdbc.domain.BaseEntity;
import com.cms.manage.vo.SysMenuMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ydf Created by 2022/2/17 15:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@ApiModel(value="菜单对象")
@EqualsAndHashCode(callSuper = true)
public class SysMenuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1481761112340736509L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父节点ID (0为顶级菜单)")
    private String parentId;

    @ApiModelProperty(value = "路由地址（以/开头）")
    private String path;

    @ApiModelProperty(value = "路由名称（字符串）")
    private String name;

    @ApiModelProperty(value = "组件名称")
    private String component;

    @ApiModelProperty(value = "排序序号")
    private Integer orderNum;

    @ApiModelProperty(value = "授权标识符（增加权限控制细粒度）")
    private String code;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型：菜单->>>menu 按钮->>>button")
    private String type;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "隐藏菜单")
    private Boolean hidden = false;

    @ApiModelProperty(value = "隐藏面包屑")
    private Boolean hiddenBreadcrumb = false;

    @ApiModelProperty(value = "菜单元数据")
    @TableField(exist = false)
    private SysMenuMeta meta;

    @ApiModelProperty(value = "子节点集合")
    @TableField(exist = false)
    private List<SysMenuEntity> children = new ArrayList<>();
}
