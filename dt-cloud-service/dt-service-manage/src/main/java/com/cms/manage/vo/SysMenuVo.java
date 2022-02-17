package com.cms.manage.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ydf Created by 2022/2/17 16:15
 */
@Data
@ApiModel(value="菜单传输对象")
public class SysMenuVo {

    @ApiModelProperty(value = "路由地址（以/开头）")
    private String path;

    @ApiModelProperty(value = "路由名称（字符串）")
    private String name;

    @ApiModelProperty(value = "组件名称")
    private String component;

    @ApiModelProperty(value = "菜单元数据")
    private SysMenuMetaVo meta;

    @ApiModelProperty(value = "子节点集合")
    @TableField(exist = false)
    private List<SysMenuVo> children = new ArrayList<>();
}
