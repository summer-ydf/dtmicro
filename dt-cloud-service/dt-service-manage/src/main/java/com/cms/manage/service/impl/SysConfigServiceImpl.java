package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.builder.SettingBuilders;
import com.cms.common.core.builder.SettingModel;
import com.cms.common.core.cache.CacheUtils;
import com.cms.common.core.domain.Params;
import com.cms.common.core.domain.SysConfig;
import com.cms.common.jdbc.config.IdGenerator;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysConfigEntity;
import com.cms.manage.mapper.SysConfigMapper;
import com.cms.manage.service.SysConfigService;
import com.cms.manage.utils.ConfigPropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static com.cms.common.tool.constant.ConstantCode.EHCACHE_CONFIG_KEY;
import static com.cms.common.tool.constant.ConstantCode.EHCACHE_CONFIG_NAME;

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
        if (!CollectionUtils.isEmpty(configs)) {
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
        Params params = ConfigPropertyUtils.params();
        if (!CollectionUtils.isEmpty(sysConfigs)) {
            // 更新数据库
            for (SysConfig c : sysConfigs) {
                SysConfigEntity configEntity = this.baseMapper.selectOne(new QueryWrapper<SysConfigEntity>().eq("k", c.getK()));
                if (!ObjectUtils.isEmpty(configEntity)) {
                    // 更新
                    configEntity.setV(c.getV());
                    this.baseMapper.updateById(configEntity);
                }else {
                    // 新增
                    SysConfigEntity sys = new SysConfigEntity();
                    sys.setId(IdGenerator.generateId());
                    sys.setK(c.getK());
                    sys.setV(c.getV());
                    this.baseMapper.insert(sys);
                }
            }
            // 删除缓存中的旧数据
            CacheUtils.remove(EHCACHE_CONFIG_NAME, EHCACHE_CONFIG_KEY);
            // 参数回调
            List<SettingModel> models = SettingBuilders.settingModel(sysConfigs);
            for (SettingModel model : models) {
                if(model.getCallbacker() != null){
                    model.getCallbacker().callback(params.get(model.getKey()),model.getValue());
                }
            }
        }
        return ResultUtil.success();
    }

    @Override
    public List<SysConfig> selectConfigList() {
        return this.baseMapper.selectConfigList();
    }
}
