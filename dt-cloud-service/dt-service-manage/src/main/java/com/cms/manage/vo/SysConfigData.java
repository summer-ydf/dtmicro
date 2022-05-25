package com.cms.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author DT辰白
 * @date 2022/4/17 14:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "系统配置封装返回参数")
public class SysConfigData implements Serializable {

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "配置value")
    private List<ConfigModel> configModels;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ConfigModel {

        @ApiModelProperty(value = "ID")
        private String id;

        @ApiModelProperty(value = "配置key")
        private String k;

        @ApiModelProperty(value = "配置value")
        private String v;

        @ApiModelProperty(value = "配置标签名称")
        private String title;

        @ApiModelProperty(value = "配置输入框提示语")
        private String placeholder;
    }
}
