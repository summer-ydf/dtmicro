package com.cms.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/1/12 11:16
 */
@Data
@Builder
@TableName("oauth_client_details")
@ApiModel(value="OAuth2客户端对象")
public class OauthClientDetails implements Serializable {

    @TableId(value = "client_id", type = IdType.INPUT)
    @ApiModelProperty(value = "客户端标识")
    private String clientId;

    @ApiModelProperty(value = "客户端所能访问的资源id集合")
    private String resourceIds;

    @ApiModelProperty(value = "备注")
    private String clientSecret;

    @ApiModelProperty(value = "权限范围")
    private String scope;

    @ApiModelProperty(value = "授权类型")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "重定向URI")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "权限值")
    private String authorities;

    @ApiModelProperty(value = "访问有效时间值(单位:秒)")
    private Integer accessTokenValidity = 7200;

    @ApiModelProperty(value = "刷新有效时间值(单位:秒)")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "附加信息")
    private String additionalInformation;

    @ApiModelProperty(value = "是否自动Approval操作")
    private String autoapprove = "true";
}
