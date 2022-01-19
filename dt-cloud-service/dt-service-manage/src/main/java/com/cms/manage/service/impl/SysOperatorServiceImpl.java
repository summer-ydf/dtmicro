package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.entity.SysOperatorRoleEntity;
import com.cms.manage.mapper.SysOperatorMapper;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.vo.SysOperatorPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
@Service
public class SysOperatorServiceImpl extends ServiceImpl<SysOperatorMapper, SysOperatorEntity> implements SysOperatorService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public ResultUtil<IPage<SysOperatorEntity>> pageSearch(SysOperatorPage request) {
        Page<SysOperatorEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysOperatorEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysOperatorEntity> saveOperator(SysOperatorEntity request) {
        SysOperatorEntity operator = this.baseMapper.selectOne(new QueryWrapper<SysOperatorEntity>().eq("username",request.getUsername()));
        if(!ObjectUtils.isEmpty(operator)) {
            return ResultUtil.error("账号已经存在！");
        }
        operator.setPassword(passwordEncoder.encode(request.getPassword()));
        this.baseMapper.insert(operator);
        // 添加角色用户关联信息
//        SysOperatorRoleEntity.builder().id().roleId(request).userId(operator.getId()).build();
//        sysUserRoleService.save();
        return ResultUtil.success(request);
    }
}
