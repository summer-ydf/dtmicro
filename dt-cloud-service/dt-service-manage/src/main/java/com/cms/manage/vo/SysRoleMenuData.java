package com.cms.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色权限接收对象
 * @author DT
 * @date 2022/3/20 17:51
 */
@Data
public class SysRoleMenuData  implements Serializable {

    private Long roleId;

    private List<String> menuIds;
}
