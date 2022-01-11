package com.cms.auth.domain;

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
    /**
     * 授权范围
     */
    String scope;
    /**
     * 第三方平台ID
     */
    String openid;
}
