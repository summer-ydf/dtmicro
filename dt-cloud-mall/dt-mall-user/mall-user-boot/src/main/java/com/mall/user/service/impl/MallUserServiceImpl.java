package com.mall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.user.entity.MallUser;
import com.mall.user.mapper.MallUserMapper;
import com.mall.user.service.MallUserService;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
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
        // 手动try/catch 必须手动回滚事务，catch事务异常类必须使用：TransactionException 否则也会无法回滚 ： 手动回滚不推荐，应该统一使用fallback来处理
//        try {
//            log.info("=============FILE START=================");
//            log.info("当前事务XID: "+RootContext.getXID());
//            //int a = 10/0;
//            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
//            log.info("=============FILE END=================");
//        } catch (TransactionException ex) {
//            ex.printStackTrace();
//        }
        int a = 10/0;
        log.info("修改用户金额成功->>>");
    }
}
