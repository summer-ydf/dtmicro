package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.vo.SysMenuVo;

import java.util.List;

/**
 * @author ydf Created by 2022/2/17 15:50
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    ResultUtil<List<SysMenuVo>> listOperatorMenu(String userId);
}
