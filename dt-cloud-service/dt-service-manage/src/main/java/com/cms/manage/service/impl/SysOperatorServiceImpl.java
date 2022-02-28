package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.entity.SysOperatorRoleEntity;
import com.cms.manage.mapper.SysOperatorMapper;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.vo.SysOperatorPage;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ydf Created by 2022/1/7 17:22
 */
@Service
public class SysOperatorServiceImpl extends ServiceImpl<SysOperatorMapper, SysOperatorEntity> implements SysOperatorService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<SecurityClaimsUserEntity> loadUserByUsername(String username, String scope) {
        System.out.println("获取数据库账号密码->>>"+username);
        SysOperatorEntity operator = this.baseMapper.selectOne(new QueryWrapper<SysOperatorEntity>().eq("username", username).eq("scope",scope));
        if(!ObjectUtils.isEmpty(operator)) {
            SecurityClaimsUserEntity securityClaimsUser = SecurityClaimsUserEntity.builder()
                    .userid(operator.getId())
                    .username(operator.getUsername())
                    .password(operator.getPassword())
                    .scope(operator.getScope())
                    .isAccountNonExpired(operator.isAccountNonExpired())
                    .isCredentialsNonExpired(operator.isCredentialsNonExpired())
                    .isAccountNonLocked(operator.isAccountNonLocked())
                    .isEnabled(operator.isEnabled())
                    .build();
            return ResultUtil.success(securityClaimsUser);
        }
        return ResultUtil.error("登录失败,未找到登录用户");
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<IPage<SysOperatorEntity>> pageSearch(SysOperatorPage request) {
        Page<SysOperatorEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysOperatorEntity> list = this.baseMapper.pageSearch(page,request);
        List<SysOperatorEntity> records = list.getRecords();
        records.forEach(sysOperatorEntity -> {
//            sysOperatorEntity.getId(),
            // SELECT GROUP_CONCAT(b.role_id) roleIds FROM sys_operator_role b WHERE b.user_id = 261749792505925
            // SELECT t.username,GROUP_CONCAT(c.role_id)
            //FROM sys_operator AS t
            // LEFT JOIN sys_operator_role AS c
            // ON t.id=c.user_id GROUP BY t.username
            List<Long> ids = new ArrayList<>();
            ids.add(1L);
            ids.add(2L);
            sysOperatorEntity.setRoleIds(ids);
        });
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysOperatorEntity> saveOperator(SysOperatorEntity request) {
        SysOperatorEntity operator = this.baseMapper.selectOne(new QueryWrapper<SysOperatorEntity>().eq("username",request.getUsername()));
        if(!ObjectUtils.isEmpty(operator)) {
            return ResultUtil.error("账号已经存在！");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        this.baseMapper.insert(request);
        // 添加操作员角色关联信息
        for (Long roleId : request.getRoleIds()) {
            this.baseMapper.saveOperatorRole(SysOperatorRoleEntity.builder()
                    .id(YitIdHelper.nextId()).roleId(roleId).userId(request.getId()).build());
        }
        return ResultUtil.success(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysOperatorEntity> deleteOperatorById(String id) {
        this.baseMapper.deleteById(id);
        this.baseMapper.deleteOperatorRoleByUserId(id);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysOperatorEntity> updateOperatorById(SysOperatorEntity request) {
//        this.baseMapper.updateOperatorRoleByUserId(request.getId(),request.getRoleId());
//        this.baseMapper.updateById(request);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> deleteBath(long[] ids) {
        this.baseMapper.deleteBath(ids);
        this.baseMapper.deleteBathOperatorRole(ids);
        return ResultUtil.success("批量删除成功");
    }
}
