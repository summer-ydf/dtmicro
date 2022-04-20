package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.builder.SettingModel;
import com.cms.common.core.cache.CacheUtils;
import com.cms.common.core.domain.Params;
import com.cms.common.core.domain.SysConfig;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysConfigEntity;
import com.cms.manage.mapper.SysConfigMapper;
import com.cms.manage.service.SysConfigService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DT
 * @date 2022/4/17 13:47
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigEntity> implements SysConfigService {

    private static final String DEFAULT_CONFIG = "default_config";
    private static final String CACHE_SETTINGS = "cache_settings";

    @Override
    public Params configParams() {
        Params cache = (Params)CacheUtils.get(CACHE_SETTINGS,DEFAULT_CONFIG);
        if(cache == null) {
            List<SysConfig> configs = this.baseMapper.selectConfigList();
            cache = SettingBuilders.settingMap(configs);
            CacheUtils.put(CACHE_SETTINGS,DEFAULT_CONFIG,cache);
        }
        return cache;
    }

    @Override
    public ResultUtil<?> listConfigs() {
        List<SysConfigEntity> configs = this.baseMapper.selectList(null);
        List<SysConfig> configList = new ArrayList<>();
        if (!configs.isEmpty()) {
            configs.forEach(c -> {
                SysConfig config = new SysConfig();
                config.setId(c.getId());
                config.setK(c.getK());
                config.setV(c.getV());
                configList.add(config);
            });
        }
        return ResultUtil.success(SettingBuilders.settingList(configList));
    }

    @Override
    public ResultUtil<?> saveConfigs(List<SysConfig> sysConfigs) {
        // 缓存里面取出旧的数据
        Params params = configParams();
        if (!sysConfigs.isEmpty()) {
            // 更新数据库
            for (SysConfig c : sysConfigs) {
                SysConfigEntity sys = new SysConfigEntity();
                sys.setK(c.getK());
                sys.setV(c.getV());
                this.baseMapper.update(sys, new QueryWrapper<SysConfigEntity>().eq("k", c.getK()));
            }
            // 删除缓存中的旧数据
            CacheUtils.remove(CACHE_SETTINGS, DEFAULT_CONFIG);
            // 参数回调
            List<SettingModel> models = SettingBuilders.settingModel(sysConfigs);
            for (SettingModel model : models) {
                if(model.getCallbacker()!=null){
                    model.getCallbacker().callback(params.get(model.getKey()),model.getValue());
                }
            }
        }
        return ResultUtil.success();
    }
}
