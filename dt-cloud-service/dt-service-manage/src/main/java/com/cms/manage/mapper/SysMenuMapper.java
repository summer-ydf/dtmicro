package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cms.manage.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ydf Created by 2022/2/17 15:51
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> findOperatorMenuByUserId(String userId,Boolean flag);

    List<SysMenuEntity> listMenu();

    Integer maxSort();

    void deleteBath(@Param("ids") List<String> ids);

    Integer countRoleMenuByMenuId(String id);
}
