package com.cms.common.core.domain.search;

import com.cms.common.core.domain.PageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作员分页、查询条件参数
 * @author ydf Created by 2022/1/13 13:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperatorPage extends PageEntity {

    /**
     * 账号
     */
    private String username;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 数据权限参数
     */
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
