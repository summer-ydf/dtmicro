package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import java.util.Map;

/**
 * @author ydf Created by 2022/2/17 15:50
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    ResultUtil<Map<String,Object>> listOperatorMenu(String userId);
}