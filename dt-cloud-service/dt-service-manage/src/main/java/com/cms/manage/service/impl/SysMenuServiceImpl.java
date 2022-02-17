package com.cms.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.mapper.SysMenuMapper;
import com.cms.manage.service.SysMenuService;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ydf Created by 2022/2/17 15:50
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @SneakyThrows
    @Override
    public ResultUtil<Map<String,Object>> listOperatorMenu(String userId) {
        Map<String,Object> objectMap = new HashMap<>(2);
        List<SysMenuEntity> sysOperatorMenuList = this.baseMapper.findOperatorMenuByUserId(userId);
        List<SysMenuEntity> buildTreeData = buildTree(sysOperatorMenuList, "0");
        List<SysMenuEntity> menuList = buildTreeData.stream().filter(u -> "menu".equals(u.getType())).collect(Collectors.toList());
        // 获取菜单数据
        objectMap.put("menu", menuList);
        // 获取按钮权限数据
        File permissionsJsonFile = ResourceUtils.getFile("classpath:permissions.json");
        String permissionsJsonInfo = FileUtils.readFileToString(permissionsJsonFile);
        objectMap.put("permissions",JSON.parseArray(permissionsJsonInfo));
        return ResultUtil.success(objectMap);
    }

    public static List<SysMenuEntity> buildTree(List<SysMenuEntity> list, String pid) {
        List<SysMenuEntity> children = list.stream().filter(x -> x.getChildren() != null && x.getParentId().equals(pid)).collect(Collectors.toList());
        List<SysMenuEntity> subclass = list.stream().filter(x -> x.getChildren() != null && !x.getParentId().equals(pid)).collect(Collectors.toList());
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
