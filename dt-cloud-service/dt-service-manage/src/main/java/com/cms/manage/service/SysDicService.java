package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysDicEntity;

/**
 * @author ydf Created by 2022/3/30 11:19
 */
public interface SysDicService extends IService<SysDicEntity> {

    ResultUtil<IPage<SysDicEntity>> pageSearch(SysSearchPage request);

    ResultUtil<SysDicEntity> saveDic(SysDicEntity request);

    ResultUtil<SysDicEntity> deleteDicById(Long id);

    ResultUtil<?> deleteBath(long[] ids);

    ResultUtil<?> updateEnabled(Long id, Boolean enabled);
}
