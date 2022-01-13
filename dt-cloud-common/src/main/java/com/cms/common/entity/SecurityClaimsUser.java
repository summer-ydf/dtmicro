package com.cms.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/1/7 16:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityClaimsUser implements Serializable {

    private static final long serialVersionUID = -7487458381816891683L;

    //@ApiModelProperty(value = "授权范围")
    String scope;

    //@ApiModelProperty(value = "登录用户账号")
    String username;

    //@ApiModelProperty(value = "登录用户密码")
    String password;

    //@ApiModelProperty(value = "登录用户ID")
    String userid;

    //@ApiModelProperty(value = "登录token")
    String jti;
}
