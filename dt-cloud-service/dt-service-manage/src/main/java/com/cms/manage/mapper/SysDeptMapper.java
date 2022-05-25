package com.cms.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cms.manage.entity.SysDepartmentEntity;
import com.cms.manage.vo.SysDeptRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统部门数据接口
 * @author DT辰白
 * @date 2021/6/13 12:03
 */
public interface SysDeptMapper extends BaseMapper<SysDepartmentEntity> {

    List<SysDepartmentEntity> queryList(@Param("request") SysDeptRequest request);

    void deleteBath(@Param("array") long[] ids);
}
