package com.cms.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysDepartmentEntity;
import com.cms.manage.vo.SysDeptRequest;

import java.util.List;

/**
 * 系统部门服务接口
 * @author DT
 * @date 2021/6/13 12:03
 */
public interface SysDeptService extends IService<SysDepartmentEntity> {

    ResultUtil<List<SysDepartmentEntity>> queryList(SysDeptRequest request);

    ResultUtil<SysDepartmentEntity> getDeptById(String id);

    ResultUtil<SysDepartmentEntity> deleteDept(String id);

    ResultUtil<SysDepartmentEntity> saveDept(SysDepartmentEntity sysDepartmentEntity);
}
