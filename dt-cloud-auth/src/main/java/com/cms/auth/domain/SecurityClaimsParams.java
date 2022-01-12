package com.cms.auth.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/1/11 15:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityClaimsParams implements Serializable {

    @ApiModelProperty(value = "授权范围")
    private String scope;

    @ApiModelProperty(value = "第三方平台ID")
    private String openid;
}
