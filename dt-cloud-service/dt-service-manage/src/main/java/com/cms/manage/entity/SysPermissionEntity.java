package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cms.modular.entity.BaseEntity;
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
 * 系统：菜单权限实体
 * @author DT
 * @date 2021/6/4 23:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_permission")
@ApiModel(value="菜单权限对象")
@EqualsAndHashCode(callSuper = true)
public class SysPermissionEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1481761112340736509L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "父节点ID (0为顶级菜单)")
    private Long parentId;

    @ApiModelProperty(value = "父节点名称")
    private String parentName;

    @ApiModelProperty(value = "权限名称")
    private String label;

    @ApiModelProperty(value = "授权标识符（增加权限控制细粒度）")
    private String code;

    @ApiModelProperty(value = "路由地址（以/开头）")
    private String path;

    @ApiModelProperty(value = "路由名称（字符串）")
    private String name;

    @ApiModelProperty(value = "授权路径（vue组件路径）")
    private String url;

    @ApiModelProperty(value = "排序序号")
    private Integer orderNum;

    @ApiModelProperty(value = "类型(0 目录 1菜单，2按钮)")
    private String type;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "权限列表")
    @TableField(exist = false)
    private List<SysPermissionEntity> children = new ArrayList<>();

}
