package com.cms.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/1/7 14:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_security_user")
public class SecurityClaimsUser implements Serializable {

    private static final long serialVersionUID = -7487458381816891683L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    String scope;
    String username;//登录用户账号
    String password;
    String userid;//登录用户ID
    String jti;//登录token id
}
