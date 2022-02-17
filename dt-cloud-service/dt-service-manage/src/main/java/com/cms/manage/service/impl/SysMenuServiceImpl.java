package com.cms.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.common.tool.result.ResultUtil;
import com.cms.manage.entity.SysMenuEntity;
import com.cms.manage.mapper.SysMenuMapper;
import com.cms.manage.service.SysMenuService;
import com.cms.manage.vo.SysMenuMetaVo;
import com.cms.manage.vo.SysMenuVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ydf Created by 2022/2/17 15:50
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Override
    public ResultUtil<List<SysMenuVo>> listOperatorMenu(String userId) {
        List<SysMenuVo> menuVoList = new ArrayList<>();
        List<SysMenuEntity> sysOperatorMenuList = this.baseMapper.findOperatorMenuByUserId(userId);
        List<SysMenuEntity> buildTreeData = buildTree(sysOperatorMenuList, "0");
        List<SysMenuEntity> menuList = buildTreeData.stream().filter(u -> "menu".equals(u.getType())).collect(Collectors.toList());
        System.out.println("过滤菜单数据->>>"+menuList);
        if(!menuList.isEmpty()) {
            menuList.forEach(d -> {
                SysMenuVo menuVo = new SysMenuVo();
                menuVo.setName(d.getName());
                menuVo.setPath(d.getPath());
                //menuVo.setChildren();
                menuVo.setMeta(SysMenuMetaVo.builder().title(d.getTitle()).icon(d.getIcon()).type(d.getType()).build());
                if(StringUtils.isNotBlank(d.getComponent())) {
                    menuVo.setComponent(d.getComponent());
                }
                menuVoList.add(menuVo);
            });
        }
        return ResultUtil.success(menuVoList);
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
