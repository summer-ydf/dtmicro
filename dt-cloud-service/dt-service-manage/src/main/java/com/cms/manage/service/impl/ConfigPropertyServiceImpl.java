package com.cms.manage.service.impl;

import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.cache.CacheUtils;
import com.cms.common.core.domain.Params;
import com.cms.common.core.domain.SysConfig;
import com.cms.manage.service.ConfigPropertyService;
import com.cms.manage.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cms.common.tool.constant.ConstantCode.EHCACHE_CONFIG_KEY;
import static com.cms.common.tool.constant.ConstantCode.EHCACHE_CONFIG_NAME;

/**
 * @author DT辰白
 * @date 2022/5/21 19:26
 */
@Service
public class ConfigPropertyServiceImpl implements ConfigPropertyService {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public Params configParams() {
        Params cache = (Params) CacheUtils.get(EHCACHE_CONFIG_NAME,EHCACHE_CONFIG_KEY);
        if(cache == null) {
            List<SysConfig> configs = sysConfigService.selectConfigList();
            cache = SettingBuilders.settingMap(configs);
            CacheUtils.put(EHCACHE_CONFIG_NAME,EHCACHE_CONFIG_KEY,cache);
        }
        return cache;
    }
}
