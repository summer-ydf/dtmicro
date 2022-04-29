package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysRoleDataScope;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.datascope.annotation.DataScope;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.domain.SysDataScopeVoEntity;
import com.cms.common.tool.enums.AuthenticationIdentityEnum;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.entity.SysOperatorRoleEntity;
import com.cms.manage.entity.SysRoleEntity;
import com.cms.manage.mapper.SysOperatorMapper;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.service.SysRoleService;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<SecurityClaimsUserEntity> loadUserByUsername(String username, String scope) {
        System.out.println("获取数据库账号密码->>>"+username);
        SysOperatorEntity operator = this.baseMapper.selectOne(new QueryWrapper<SysOperatorEntity>().eq("username", username).eq("scope",scope));
        if(!ObjectUtils.isEmpty(operator)) {
            // 查询操作员角色数据权限
            List<SysRoleDataScope> roleDataScopes = this.baseMapper.selectRoleDataScopeByUserId(operator.getId());
            System.out.println("查询操作员角色数据权限->>>"+roleDataScopes);
            List<SysDataScopeVoEntity> roles = new ArrayList<>();
            if (!CollectionUtils.isEmpty(roleDataScopes)) {
                for (SysRoleDataScope data : roleDataScopes) {
                    roles.add(SysDataScopeVoEntity.builder().roleId(data.getRoleId()).dataScope(data.getDataScope()).build());
                }
            }
            SecurityClaimsUserEntity securityClaimsUser = SecurityClaimsUserEntity.builder()
                    .userid(operator.getId())
                    .username(operator.getUsername())
                    .password(operator.getPassword())
                    .scope(operator.getScope())
                    .deptId(operator.getDeptId())
                    .isAccountNonExpired(operator.isAccountNonExpired())
                    .isCredentialsNonExpired(operator.isCredentialsNonExpired())
                    .isAccountNonLocked(operator.isAccountNonLocked())
                    .isEnabled(operator.isEnabled())
                    .isAdmin(operator.isAdmin())
                    .roles(roles)
                    .mobile(operator.getMobile())
                    .idno(operator.getIdno())
                    .openid(operator.getOpenid())
                    // 系统后台管理端标识
                    .authenticationIdentity(AuthenticationIdentityEnum.USERNAME.getValue())
                    .build();
            return ResultUtil.success(securityClaimsUser);
        }
        return ResultUtil.error("用户不存在");
    }

    @Override
    @DataScope(deptAlias = "p",userAlias = "t")
    @Transactional(readOnly = true)
    public ResultUtil<IPage<SysOperatorEntity>> pageSearch(SysSearchPage request) {
        Page<SysOperatorEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysOperatorEntity> list = this.baseMapper.pageSearch(page,request);
        if(!CollectionUtils.isEmpty(list.getRecords())) {
            for (SysOperatorEntity operator : list.getRecords()) {
                List<Long> roleIds =  this.baseMapper.selectOperatorAndRoleById(operator.getId());
                if (!CollectionUtils.isEmpty(roleIds)) {
                    List<String> resultList = new ArrayList<>();
                    roleIds.forEach(id -> {
                        SysRoleEntity serviceOne = sysRoleService.getOne(new QueryWrapper<SysRoleEntity>().eq("id", id));
                        resultList.add(serviceOne.getName());
                    });
                    operator.setRoleNames(resultList);
                }
                operator.setRoleIds(roleIds);
            }
        }
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysOperatorEntity> saveOperator(SysOperatorEntity request) {
        if(null != request.getId()) {
            // 删除旧数据
            this.baseMapper.removeOperatorRoleByUserId(request.getId());
            // 添加操作员角色关联信息
            this.insertOperatorRole(request);
            // 更新操作员
            this.baseMapper.updateById(request);
            return ResultUtil.success();
        }
        SysOperatorEntity operator = this.baseMapper.selectOne(new QueryWrapper<SysOperatorEntity>().eq("username",request.getUsername()));
        if(!ObjectUtils.isEmpty(operator)) {
            return ResultUtil.error("账号已经存在！");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        // 添加操作员
        this.baseMapper.insert(request);
        // 添加操作员角色关联信息
        this.insertOperatorRole(request);
        return ResultUtil.success(request);
    }

    private void insertOperatorRole(SysOperatorEntity request) {
        List<Long> roleIds = request.getRoleIds();
        for (Long roleId : roleIds) {
            this.baseMapper.saveOperatorRole(SysOperatorRoleEntity.builder().id(YitIdHelper.nextId()).roleId(roleId).userId(request.getId()).build());
        }
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
    public ResultUtil<?> deleteBath(long[] ids) {
        this.baseMapper.deleteBath(ids);
        this.baseMapper.deleteBathOperatorRole(ids);
        return ResultUtil.success("批量删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> updateEnabled(Long id, Boolean enabled) {
        SysOperatorEntity operator = this.baseMapper.selectById(id);
        operator.setEnabled(enabled);
        this.baseMapper.updateById(operator);
        return ResultUtil.success();
    }

}
