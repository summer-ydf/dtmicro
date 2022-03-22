package com.cms.common.tool.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author ydf Created by 2022/3/22 17:53
 */
@Data
@Builder
public class SysDataScopeVoEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 数据权限范围
     */
    private String dataScope;
}
