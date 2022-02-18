package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.mapper.SysMenuMapper;
import com.cms.manage.service.SysMenuService;
import com.cms.manage.vo.SysMenuMeta;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        // 获取菜单数据
        List<SysMenuEntity> sysOperatorMenuList = this.baseMapper.findOperatorMenuByUserId(userId,true);
        List<SysMenuEntity> buildTreeData = buildTree(sysOperatorMenuList, "0");
        objectMap.put("menu", buildTreeData);
        // 获取按钮权限数据
        List<SysMenuEntity> sysOperatorMenuListAll = this.baseMapper.findOperatorMenuByUserId(userId,false);
        String[] codes = sysOperatorMenuListAll.stream().map(SysMenuEntity::getCode).filter(Objects::nonNull).toArray(String[]::new);
        objectMap.put("permissions",codes);
        return ResultUtil.success(objectMap);
    }

    public static List<SysMenuEntity> buildTree(List<SysMenuEntity> list, String pid) {
        List<SysMenuEntity> children = list.stream().filter(x -> x.getChildren() != null && x.getParentId().equals(pid)).collect(Collectors.toList());
        List<SysMenuEntity> subclass = list.stream().filter(x -> x.getChildren() != null && !x.getParentId().equals(pid)).collect(Collectors.toList());
        if(children.size() > 0) {
            children.forEach(x -> {
                // 构造菜单元数据
                x.setMeta(SysMenuMeta.builder().title(x.getTitle()).icon(x.getIcon()).type(x.getType()).build());
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
