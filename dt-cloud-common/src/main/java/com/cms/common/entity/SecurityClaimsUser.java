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

    String scope;
    String username;//登录用户账号
    String password;
    String userid;//登录用户ID
    String jti;//登录token id
}
