package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysOauthClientEntity;
import com.cms.manage.mapper.SysOauthClientMapper;
import com.cms.manage.service.SysOauthClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;




/**
 * @author ydf Created by 2022/4/28 15:06
 */
@Service
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper, SysOauthClientEntity> implements SysOauthClientService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<IPage<SysOauthClientEntity>> pageSearch(SysSearchPage request) {
        Page<SysOauthClientEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysOauthClientEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysOauthClientEntity> saveOauthClient(SysOauthClientEntity request) {
        SysOauthClientEntity clientEntity = this.baseMapper.selectById(request.getClientId());
        if (!ObjectUtils.isEmpty(clientEntity)) {
            if (!clientEntity.getClientSecret().equals(request.getClientSecret())) {
                // 客户端密钥修改
                request.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
            }
            request.setAuthorizedGrantTypes(StringUtils.join(request.getAuthorizedGrantTypesArray().toArray(), ","));
            this.baseMapper.updateById(request);
            return ResultUtil.success();
        }
        SysOauthClientEntity sysOauthClientEntity = new SysOauthClientEntity();
        sysOauthClientEntity.setClientId(request.getClientId());
        sysOauthClientEntity.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
        sysOauthClientEntity.setAutoapprove("true");
        sysOauthClientEntity.setScope(request.getScope());
        sysOauthClientEntity.setAuthorizedGrantTypes(StringUtils.join(request.getAuthorizedGrantTypesArray().toArray(), ","));
        sysOauthClientEntity.setAccessTokenValidity(request.getAccessTokenValidity());
        sysOauthClientEntity.setRefreshTokenValidity(request.getRefreshTokenValidity());
        this.baseMapper.insert(sysOauthClientEntity);
        return ResultUtil.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysOauthClientEntity> deleteOauthClientByClientId(String clientId) {
        this.baseMapper.deleteById(clientId);
        return ResultUtil.success();
    }
}
