package com.cms.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 客户端实体对象
 * @author ydf Created by 2022/4/28 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("oauth_client_details")
@ApiModel(value="操作员对象")
public class SysOauthClientEntity implements Serializable {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "客户端密钥")
    private String clientSecret;

    @ApiModelProperty(value = "资源id列表")
    private String resourceIds;

    @ApiModelProperty(value = "域")
    private String scope;

    @ApiModelProperty(value = "授权方式")
    private String authorizedGrantTypes;

    @ApiModelProperty("回调地址")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "权限列表")
    private String authorities;

    @ApiModelProperty(value = "认证令牌时效")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "刷新令牌时效")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "扩展信息")
    private String additionalInformation;

    @ApiModelProperty(value = "是否自动放行")
    private String autoapprove;
}
