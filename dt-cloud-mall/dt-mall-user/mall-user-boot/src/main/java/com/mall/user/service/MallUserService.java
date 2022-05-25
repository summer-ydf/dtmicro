package com.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.user.entity.MallUser;

/**
 * @author DT辰白 Created by 2021/12/22 13:52
 */
public interface MallUserService extends IService<MallUser> {

    void reduceMoney(Integer id);
}
