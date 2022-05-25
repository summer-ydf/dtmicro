package com.cms.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author DT辰白 Created by 2022/3/23 14:12
 */
@Data
public class SysRoleScope implements Serializable {

    private Long roleId;

    private Long dataScope;

    private List<String> deptIds;
}
