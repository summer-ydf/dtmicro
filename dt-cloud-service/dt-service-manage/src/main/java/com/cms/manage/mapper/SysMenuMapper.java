package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cms.manage.entity.SysMenuEntity;

import java.util.List;

/**
 * @author ydf Created by 2022/2/17 15:51
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> findOperatorMenuByUserId(String userId,Boolean flag);

    List<SysMenuEntity> listMenu();

    Integer maxSort();
}
