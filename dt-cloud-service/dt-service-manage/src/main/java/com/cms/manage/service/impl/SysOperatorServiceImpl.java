package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.mapper.SysOperatorMapper;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.vo.SysOperatorPage;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
@Service
public class SysOperatorServiceImpl extends ServiceImpl<SysOperatorMapper, SysOperatorEntity> implements SysOperatorService {

    @Override
    public ResultUtil<SecurityClaimsUser> loadUserByUsername(String username) {
        System.out.println("获取数据库账号密码->>>"+username);
        SysOperatorEntity operator = this.baseMapper.selectOne(new QueryWrapper<SysOperatorEntity>().eq("username", username));
        if(!ObjectUtils.isEmpty(operator)) {
            SecurityClaimsUser securityClaimsUser = SecurityClaimsUser.builder().username(username).password(operator.getPassword()).scope("web").build();
            return ResultUtil.success(securityClaimsUser);
        }
        return ResultUtil.error("登录失败,未找到登录用户");
    }

    @Override
    public ResultUtil<IPage<SysOperatorEntity>> pageSearch(SysOperatorPage request) {
        Page<SysOperatorEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysOperatorEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }
}
