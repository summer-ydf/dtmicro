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
     * 授权范围
     */
    private String scope;

    /**
     * 登录用户账号
     */
    private String username;

    /**
     * 登录用户密码
     */
    private String password;

    /**
     * 登录用户ID
     */
    private Long userid;

    /**
     * 登录用户头像
     */
    private String avatar;

    /**
     * 帐户是否过期(1 未过期，0已过期)
     */
    private boolean isAccountNonExpired;

    /**
     * 帐户是否被锁定(1 未锁定，0已锁定)
     */
    private boolean isAccountNonLocked = true;

    /**
     * 密码是否过期(1 未过期，0已过期)
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * 帐户是否可用(1 可用，0 删除用户)
     */
    private boolean isEnabled = true;

    /**
     * 登录token
     */
    String jti;
}
