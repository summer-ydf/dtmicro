package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysLogLoginEntity;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
public interface SysLogLoginService extends IService<SysLogLoginEntity> {

    void saveLoginLog(SysLogLoginEntity loginLogEntity);

    ResultUtil<IPage<SysLogLoginEntity>> pageSearch(SysSearchPage request);
}
