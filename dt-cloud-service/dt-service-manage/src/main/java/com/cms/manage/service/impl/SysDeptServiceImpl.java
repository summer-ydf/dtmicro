package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.constant.ConstantCommonCode;
import com.cms.common.tool.result.ResultEnum;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysDepartmentEntity;
import com.cms.manage.entity.SysOperatorEntity;
import com.cms.manage.mapper.SysDeptMapper;
import com.cms.manage.service.SysDeptService;
import com.cms.manage.service.SysOperatorService;
import com.cms.manage.vo.SysDeptRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统部门服务实现类
 * @author DT
 * @date 2021/6/13 12:03
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDepartmentEntity> implements SysDeptService {

    @Resource
    private SysOperatorService sysOperatorService;

    @Override
    @Transactional(readOnly = true)
    public ResultUtil<List<SysDepartmentEntity>> queryList(SysDeptRequest request) {
        List<SysDepartmentEntity> entityList = this.baseMapper.queryList(request);
        if(StringUtils.isNotBlank(request.getLabel()) || null != request.getStartTime() || null != request.getEndTime()){
            return ResultUtil.success(entityList);
        }
        // 构建树结构数据返回
        List<SysDepartmentEntity> treeEntityList = new ArrayList<>();
        if(null != entityList && !entityList.isEmpty()){
            treeEntityList =  buildTree(entityList,"0");
        }
        return ResultUtil.success(treeEntityList);
    }


    @Override
    @Transactional(readOnly = true)
    public ResultUtil<SysDepartmentEntity> getDeptById(String id) {
        SysDepartmentEntity entity = this.baseMapper.selectById(id);
        return ResultUtil.success(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysDepartmentEntity> deleteDept(String id) {
        Long selectCount = this.baseMapper.selectCount(new QueryWrapper<SysDepartmentEntity>().eq("pid", id));
        if(selectCount > 0){
            return ResultUtil.error("请先删除子节点！");
        }
        long count = sysOperatorService.count(new QueryWrapper<SysOperatorEntity>().eq("dept_id", id));
        if(count > 0){
            return ResultUtil.error("该节点已有用户使用！");
        }
        int delete = this.baseMapper.deleteById(id);
        return delete > 0 ? ResultUtil.success() : ResultUtil.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<SysDepartmentEntity> saveDept(SysDepartmentEntity sysDepartmentEntity) {
        Long count = this.baseMapper.selectCount(new QueryWrapper<SysDepartmentEntity>().eq("id", sysDepartmentEntity.getId()));
        if(count > 0) {
            // 修改
            this.baseMapper.updateById(sysDepartmentEntity);
            return ResultUtil.success(sysDepartmentEntity);
        }
        if(!StringUtils.equals(sysDepartmentEntity.getParentId(), ConstantCommonCode.STR_ZERO)) {
            Long countParentId = this.baseMapper.selectCount(new QueryWrapper<SysDepartmentEntity>().eq("id", sysDepartmentEntity.getParentId()));
            if (countParentId <= 0) {
                // 上级部门不存在
                return ResultUtil.error(ResultEnum.ERROR.getCode(),"请先保存上级部门");
            }
        }
        this.baseMapper.insert(sysDepartmentEntity);
        return ResultUtil.success(sysDepartmentEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<?> deleteBath(long[] ids) {
        for (long id : ids) {
            long count = sysOperatorService.count(new QueryWrapper<SysOperatorEntity>().eq("dept_id", id));
            if(count > 0) {
                return ResultUtil.error("【"+id+"】其它用户使用中，无法删除");
            }
        }
        this.baseMapper.deleteBath(ids);
        return ResultUtil.success();
    }

    public static List<SysDepartmentEntity> buildTree(List<SysDepartmentEntity> list, String pid){
        List<SysDepartmentEntity> children = list.stream().filter(x -> x.getParentId().equals(pid)).collect(Collectors.toList());
        List<SysDepartmentEntity> subclass = list.stream().filter(x -> !x.getParentId().equals(pid)).collect(Collectors.toList());
        if(children.size() > 0){
            children.forEach(x -> {
                if(subclass.size() > 0){
                    buildTree(subclass,x.getId()).forEach(
                            y -> x.getChildren().add(y)
                    );
                }
            });
        }
        return children;
    }

}
