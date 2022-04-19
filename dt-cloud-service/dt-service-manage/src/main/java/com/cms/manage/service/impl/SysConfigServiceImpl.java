package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.builder.SettingModel;
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
        // 缓存数据
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
        // 缓存里面取出来
        Params params = SettingBuilders.settingMap(configList);
        // 参数回调
        List<SettingModel> models = SettingBuilders.settingModel(sysConfigs);
        for (SettingModel model : models) {
            if(model.getCallbacker()!=null){
                model.getCallbacker().callback(params.get(model.getKey()),model.getValue());
            }
        }
        return ResultUtil.success();
    }
}
