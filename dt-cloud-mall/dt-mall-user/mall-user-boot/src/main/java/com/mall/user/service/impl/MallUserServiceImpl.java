package com.mall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.user.entity.MallUser;
import com.mall.user.mapper.MallUserMapper;
import com.mall.user.service.MallUserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

/**
 * @author ydf Created by 2021/12/22 13:52
 */
@Service
@CommonsLog
public class MallUserServiceImpl extends ServiceImpl<MallUserMapper, MallUser> implements MallUserService {

    @Override
    public void reduceMoney(Integer id) {
        log.info("开始修改用户金额->>>");
        MallUser mallUser = this.baseMapper.selectById(id);
        double money = mallUser.getMoney() - 1;
        mallUser.setMoney(money);
        this.baseMapper.updateById(mallUser);
        int a = 10/0;
        log.info("修改用户金额成功->>>");
    }
}
