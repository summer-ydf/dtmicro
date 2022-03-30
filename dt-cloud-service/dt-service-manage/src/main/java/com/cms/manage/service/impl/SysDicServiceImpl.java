package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.constant.ConstantCode;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.PatternUtils;
import com.cms.manage.entity.SysDicEntity;
import com.cms.manage.mapper.SysDicMapper;
import com.cms.manage.service.SysDicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author ydf Created by 2022/3/30 11:19
 */
@Service
public class SysDicServiceImpl extends ServiceImpl<SysDicMapper, SysDicEntity> implements SysDicService {

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<IPage<SysDicEntity>> pageSearch(SysSearchPage request) {
        Page<SysDicEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysDicEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysDicEntity> saveDic(SysDicEntity request) {
        boolean regxCode = PatternUtils.regxCode(request.getCode());
        if (!regxCode || request.getCode().length() > ConstantCode.INT_THREE){
            return ResultUtil.error("字典代码长度不能大于3位且必须是数字");
        }
        SysDicEntity dicEntity = this.baseMapper.selectOne(new QueryWrapper<SysDicEntity>().eq("code", request.getCode()));
        if (null != request.getId()) {
            if (!ObjectUtils.isEmpty(dicEntity) && !dicEntity.getId().equals(request.getId())) {
                return ResultUtil.error("代码已存在");
            }
            this.baseMapper.updateById(request);
        }else {
            if (!ObjectUtils.isEmpty(dicEntity)) {
                return ResultUtil.error("代码已存在");
            }
            this.baseMapper.insert(request);
        }
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysDicEntity> deleteDicById(Long id) {
        this.baseMapper.deleteById(id);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> deleteBath(long[] ids) {
        this.baseMapper.deleteBathDic(ids);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> updateEnabled(Long id, Boolean enabled) {
        SysDicEntity dicEntity = this.baseMapper.selectById(id);
        dicEntity.setEnabled(enabled);
        this.baseMapper.updateById(dicEntity);
        return ResultUtil.success();
    }
}
