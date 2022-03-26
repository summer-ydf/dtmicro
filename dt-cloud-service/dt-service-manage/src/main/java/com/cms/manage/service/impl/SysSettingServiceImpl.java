package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorSettingEntity;
import com.cms.manage.mapper.SysSettingMapper;
import com.cms.manage.service.SysSettingService;
import org.springframework.stereotype.Service;

/**
 * @author DT
 * @date 2022/3/26 19:50
 */
@Service
public class SysSettingServiceImpl extends ServiceImpl<SysSettingMapper, SysOperatorSettingEntity> implements SysSettingService {

    @Override
    public ResultUtil<?> saveSetting(SysOperatorSettingEntity settingEntity) {
        if(null != settingEntity.getId()) {
            this.baseMapper.updateById(settingEntity);
            return ResultUtil.success();
        }
        this.baseMapper.insert(settingEntity);
        return ResultUtil.success();
    }
}
