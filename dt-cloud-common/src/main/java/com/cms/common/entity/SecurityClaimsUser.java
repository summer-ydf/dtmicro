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
    /**
     * 登录系统范围
     */
    String scope;
    /**
     * 登录用户账号
     */
    String username;
    /**
     * 登录用户密码
     */
    String password;
    /**
     * 登录用户ID
     */
    String userid;
    /**
     * 登录token id
     */
    String jti;
}
