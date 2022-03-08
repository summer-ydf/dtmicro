package com.cms.manage.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysDepartmentEntity;
import com.cms.manage.service.SysDeptService;
import com.cms.manage.vo.SysDeptRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DT
 * @date 2021/6/13 12:02
 */
@Api(tags = "系统部门信息API")
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    public SysDeptController(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @ApiOperation(value = "查询部门树列表")
    @GetMapping("/list")
    public ResultUtil<List<SysDepartmentEntity>> list(SysDeptRequest request){
        return sysDeptService.queryList(request);
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/save")
    public ResultUtil<SysDepartmentEntity> generateId(@RequestBody SysDepartmentEntity sysDepartmentEntity) {
        return sysDeptService.saveDept(sysDepartmentEntity);
    }

    @GetMapping("/getById/{id}")
    @ApiOperation(value = "根据id查询部门")
    public ResultUtil<SysDepartmentEntity> getById(@PathVariable String id){
        return sysDeptService.getDeptById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除系统部门")
    public ResultUtil<SysDepartmentEntity> delete(@PathVariable String id){
        return sysDeptService.deleteDept(id);
    }

}
