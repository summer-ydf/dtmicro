package com.cms.manage.controller;


import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysRoleEntity;
import com.cms.manage.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author DT
 * @date 2021/6/5 0:32
 */
@Api(tags = "角色管理API")
@RestController
@RequestMapping(value = "/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @ApiOperation(value = "查询角色权限列表")
    @GetMapping("/list")
    public ResultUtil<List<SysRoleEntity>> list(SysRoleEntity request){
        return sysRoleService.queryList(request);
    }

    //@Log(title = "新增平台系统角色", businessType = LogTypeCode.INSERT)
    @ApiOperation(value = "添加系统角色")
    @PostMapping("/save")
    public ResultUtil<SysRoleEntity> save(@RequestBody SysRoleEntity request) {
        return sysRoleService.saveRole(request);
    }

    @ApiOperation(value = "根据id查询角色")
    @GetMapping("/getById/{id}")
    public ResultUtil<SysRoleEntity> getById(@PathVariable Long id){
        return sysRoleService.getRoleById(id);
    }

    //@Log(title = "更新平台系统角色", businessType = LogTypeCode.UPDATE)
    @ApiOperation(value = "修改系统角色")
    @PutMapping("/update")
    public ResultUtil<SysRoleEntity> update(@RequestBody SysRoleEntity request) {
        return sysRoleService.updateRoleById(request);
    }

    //@Log(title = "删除平台系统角色", businessType = LogTypeCode.DELETE)
    @ApiOperation(value = "删除系统角色")
    @DeleteMapping("/delete/{id}")
    public ResultUtil<SysRoleEntity> delete(@PathVariable Long id){
        return sysRoleService.deleteRoleById(id);
    }

    @ApiOperation(value = "查询所有角色")
    @GetMapping("/findAll")
    public ResultUtil<List<SysRoleEntity>> findAll(){
        return sysRoleService.findAll();
    }
}
