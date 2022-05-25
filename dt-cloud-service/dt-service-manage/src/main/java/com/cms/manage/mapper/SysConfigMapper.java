package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cms.common.core.domain.SysConfig;
import com.cms.manage.entity.SysConfigEntity;

import java.util.List;

/**
 * @author DT辰白
 * @date 2022/4/17 13:48
 */
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {

    List<SysConfig> selectConfigList();
}
