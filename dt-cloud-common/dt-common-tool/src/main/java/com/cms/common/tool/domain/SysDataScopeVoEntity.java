package com.cms.common.tool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DT辰白 Created by 2022/3/22 17:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDataScopeVoEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 数据权限范围
     */
    private Long dataScope;
}
