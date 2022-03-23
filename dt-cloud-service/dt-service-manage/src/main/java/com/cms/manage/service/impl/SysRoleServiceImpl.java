package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.core.domain.SysSearchPage;
import com.cms.common.jdbc.config.IdGenerator;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.entity.SysRoleEntity;
import com.cms.manage.entity.SysRoleMenuEntity;
import com.cms.manage.mapper.SysRoleMapper;
import com.cms.manage.service.SysRoleService;
import com.cms.manage.vo.SysRoleMenuData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色服务实现类
 * @author DT
 * @date 2021/6/2 22:23
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Override
    public ResultUtil<IPage<SysRoleEntity>> pageSearch(SysSearchPage request) {
        Page<SysRoleEntity> page = new Page<>(request.getCurrent(),request.getSize());
        IPage<SysRoleEntity> list = this.baseMapper.pageSearch(page,request);
        return ResultUtil.success(list);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<List<SysRoleEntity>> queryList(SysRoleEntity request) {
        List<SysRoleEntity> responseList = new ArrayList<>();
        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(request.getName())) {
            wrapper.like("name",request.getName());
        }
        if(null != request.getStartTime() && null != request.getEndTime()) {
            wrapper.between("create_time"
                    ,DateFormatUtils.format(request.getStartTime(),"yyyy-MM-dd HH:mm:ss")
                    ,DateFormatUtils.format(request.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<SysRoleEntity> roleEntityList = this.baseMapper.selectList(wrapper);
        if(!roleEntityList.isEmpty()) {
            roleEntityList.forEach(it -> {
                // 根据角色ID查询角色权限信息
                List<SysMenuEntity> menuEntityList = this.baseMapper.getMenuListByRoleId(it.getId());
                List<SysMenuEntity> childrenList = buildTree(menuEntityList, "0");
                it.setChildren(childrenList);
                responseList.add(it);
            });
        }
        return ResultUtil.success(responseList);
    }

    public static List<SysMenuEntity> buildTree(List<SysMenuEntity> list, String pid){
        List<SysMenuEntity> children = list.stream().filter(x -> x.getChildren() != null && x.getParentId().equals(pid)).collect(Collectors.toList());
        List<SysMenuEntity> subclass = list.stream().filter(x -> x.getChildren() != null && !x.getParentId().equals(pid)).collect(Collectors.toList());
        if(children.size() > 0) {
            children.forEach(x -> {
                if(subclass.size() > 0) {
                    buildTree(subclass,x.getId()).forEach(y -> x.getChildren().add(y));
                }
            });
        }
        return children;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysRoleEntity> saveRole(SysRoleEntity request) {
        if (null != request.getId()) {
            this.baseMapper.updateById(request);
            return ResultUtil.success();
        }
        this.baseMapper.insert(request);
        return ResultUtil.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<SysRoleEntity> getRoleById(Long id) {
        SysRoleEntity entity = this.baseMapper.selectById(id);
        return ResultUtil.success(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysRoleEntity> deleteRoleById(Long id) {
        // 删除角色权限中间表
        List<Long> ids = this.baseMapper.listRoleMenuByRoleId(id);
        if(!ids.isEmpty()) {
            this.baseMapper.deleteRoleMenuByIds(ids);
        }
        int delete = this.baseMapper.deleteById(id);
        return delete > 0 ? ResultUtil.success() : ResultUtil.error();
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<List<SysRoleEntity>> findAll() {
        List<SysRoleEntity> entityList = this.baseMapper.selectList(null);
        return ResultUtil.success(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> deleteBath(long[] ids) {
        // 查询当前是否有操作员
        List<Long> resultIds = new ArrayList<>();
        for (long id : ids) {
            Long count = this.baseMapper.selectOperotarRoleByRoleId(id);
            if(count <= 0) {
                resultIds.add(id);
            }
        }
        if(!resultIds.isEmpty()) {
            for (Long id : resultIds) {
                // 删除角色权限中间表
                List<Long> collectIds = this.baseMapper.listRoleMenuByRoleId(id);
                if(!collectIds.isEmpty()) {
                    this.baseMapper.deleteRoleMenuByIds(collectIds);
                }
            }
        }
        // 批量删除角色
        this.baseMapper.deleteBath(resultIds);
        return ResultUtil.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<SysRoleEntity> getTreeRoleMenuById(Long id) {
        SysRoleEntity sysRoleEntity = this.baseMapper.selectById(id);
        // 根据角色ID查询角色权限信息
        List<SysMenuEntity> menuEntityList = this.baseMapper.getMenuListByRoleId(id);
        List<SysMenuEntity> childrenList = buildTree(menuEntityList, "0");
        sysRoleEntity.setChildren(childrenList);
        return ResultUtil.success(sysRoleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> saveRoleMenu(SysRoleMenuData sysRoleMenuData) {
        List<SysRoleMenuEntity> sysRoleMenuEntityList = this.baseMapper.selectRoleMenuList(sysRoleMenuData.getRoleId());
        if(!sysRoleMenuEntityList.isEmpty()) {
            // 删除旧数据
            List<Long> ids = sysRoleMenuEntityList.stream().map(SysRoleMenuEntity::getId).collect(Collectors.toList());
            this.baseMapper.deleteBathRoleMenu(ids);
        }
        // 新增角色权限信息
        sysRoleMenuData.getMenuIds().forEach(id -> {
            SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
            roleMenuEntity.setId(IdGenerator.generateId());
            roleMenuEntity.setRoleId(sysRoleMenuData.getRoleId());
            roleMenuEntity.setMenuId(id);
            this.baseMapper.insertRoleMenu(roleMenuEntity);
        });
        return ResultUtil.success();
    }
}
