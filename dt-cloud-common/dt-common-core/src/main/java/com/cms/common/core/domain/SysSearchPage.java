package com.cms.common.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页、查询条件参数
 * @author ydf Created by 2022/1/13 13:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSearchPage extends PageEntity {

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 名称
     */
    private String name;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 类别
     */
    private String category;

    /**
     * 状态
     */
    private Integer status;

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
