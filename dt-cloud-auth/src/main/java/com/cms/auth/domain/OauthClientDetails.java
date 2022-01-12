package com.cms.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/1/12 11:16
 */
@Data
@Builder
@TableName("oauth_client_details")
public class OauthClientDetails implements Serializable {

    @TableId(value = "client_id", type = IdType.INPUT)
    //@ApiDocument(remark = "客户端标识")
    private String clientId;

    //@ApiDocument(remark = "客户端所能访问的资源id集合")
    private String resourceIds;

    //@ApiDocument(remark = "备注")
    private String clientSecret;

    //@ApiDocument(remark = "权限范围")
    private String scope;

    //@ApiDocument(remark = "授权类型")
    private String authorizedGrantTypes;

    //@ApiDocument(remark = "重定向URI")
    private String webServerRedirectUri;

    //@ApiDocument(remark = "权限值")
    private String authorities;

    //@ApiDocument(remark = "访问有效时间值(单位:秒)")
    private Integer accessTokenValidity = 7200;

    //@ApiDocument(remark = "刷新有效时间值(单位:秒)")
    private Integer refreshTokenValidity;

    //@ApiDocument(remark = "附加信息")
    private String additionalInformation;

    //@ApiDocument(remark = "是否自动Approval操作")
    private String autoapprove = "true";
}
