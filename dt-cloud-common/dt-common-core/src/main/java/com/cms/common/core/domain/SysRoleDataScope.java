package com.cms.common.core.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色数据权限
 * @author DT辰白 Created by 2022/3/22 17:48
 */
@Data
@Builder
public class SysRoleDataScope implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 数据权限范围
     */
    private Long dataScope;
}
