package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.result.ResultUtil;
import com.cms.manage.entity.SysPermissionEntity;
import com.cms.manage.mapper.SysPermissionMapper;
import com.cms.manage.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统权限服务实现类
 * @author DT
 * @date 2021/6/2 22:23
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> implements SysPermissionService {

    @Override
    @Transactional(readOnly = true)
    public List<SysPermissionEntity> getPermissionListByUserId(Long userId) {
        return this.baseMapper.getPermissionListByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<List<SysPermissionEntity>> queryList(SysPermissionEntity request) {
        QueryWrapper<SysPermissionEntity> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(request.getLabel())) {
            wrapper.like("label",request.getLabel());
        }
        if(StringUtils.isNotBlank(request.getType())) {
            wrapper.eq("type",request.getType());
        }
        List<SysPermissionEntity> entityList = this.baseMapper.selectList(wrapper);
        // 查询条件
        if(StringUtils.isNotBlank(request.getLabel()) || StringUtils.isNotBlank(request.getType())) {
            return ResultUtil.success(entityList);
        }
        // 构建树结构数据返回
        List<SysPermissionEntity> treeEntityList = new ArrayList<>();
        if(null != entityList && !entityList.isEmpty()){
            treeEntityList =  buildTree(entityList, 0L);
        }
        return ResultUtil.success(treeEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysPermissionEntity> saveMenu(SysPermissionEntity request) {
        int insert = this.baseMapper.insert(request);
        return insert > 0 ? ResultUtil.success() : ResultUtil.error();
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<SysPermissionEntity> getMenuById(Long id) {
        SysPermissionEntity entity = this.baseMapper.selectById(id);
        return ResultUtil.success(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysPermissionEntity> updatePermissionById(SysPermissionEntity request) {
        int update = this.baseMapper.updateById(request);
        return update > 0 ? ResultUtil.success() : ResultUtil.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysPermissionEntity> deletePermissionById(Long id) {
        Long selectCount = this.baseMapper.selectCount(new QueryWrapper<SysPermissionEntity>().eq("parent_id", id));
        if(null != selectCount && selectCount > 0L) {
            return ResultUtil.error("请先删除子节点！");
        }
        int count = this.baseMapper.countRolePermission(id);
        if(count > 0) {
            return ResultUtil.error("该节点已有角色使用！");
        }
        int delete = this.baseMapper.deleteById(id);
        return delete > 0 ? ResultUtil.success() : ResultUtil.error();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysPermissionEntity> getPermissionListByRoleId(Long roleId) {
        return this.baseMapper.getPermissionListByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<List<SysPermissionEntity>> listTree() {
        List<SysPermissionEntity> sysPermissionEntityList = this.baseMapper.selectList(null);
        // 构建树结构数据返回
        List<SysPermissionEntity> treeEntityList = new ArrayList<>();
        if(!sysPermissionEntityList.isEmpty()) {
            treeEntityList =  buildTree(sysPermissionEntityList, 0L);
        }
        // 添加顶级节点
        SysPermissionEntity response = new SysPermissionEntity();
        response.setId(0L);
        response.setParentId(-1L);
        response.setOrderNum(0);
        response.setLabel("DTBoot管理系统");
        treeEntityList.add(response);
        // 排序输出
        return ResultUtil.success(treeEntityList.stream().sorted(Comparator.comparing(SysPermissionEntity::getOrderNum)).collect(Collectors.toList()));
    }

    public static List<SysPermissionEntity> buildTree(List<SysPermissionEntity> list, Long pid){
        List<SysPermissionEntity> children = list.stream().filter(x -> x.getChildren() != null && x.getParentId().equals(pid)).collect(Collectors.toList());
        List<SysPermissionEntity> subclass = list.stream().filter(x -> x.getChildren() != null && !x.getParentId().equals(pid)).collect(Collectors.toList());
        if(children.size() > 0) {
            children.forEach(x -> {
                if(subclass.size() > 0) {
                    buildTree(subclass,x.getId()).forEach(
                            y -> x.getChildren().add(y)
                    );
                }
            });
        }
        return children;
    }
}
