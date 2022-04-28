package com.cms.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOauthClientEntity;

/**
 * @author ydf Created by 2022/4/28 15:06
 */
public interface SysOauthClientService extends IService<SysOauthClientEntity> {

    ResultUtil<IPage<SysOauthClientEntity>> pageSearch(SysSearchPage request);

    ResultUtil<SysOauthClientEntity> saveOauthClient(SysOauthClientEntity request);

    ResultUtil<SysOauthClientEntity> deleteOauthClientByClientId(String clientId);
}
