package com.cms.item.service.impl;


import com.api.item.feign.ItemFeignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cms.common.result.ResultUtil;
import com.cms.item.entity.TestOrderEntity;
import com.cms.item.mapper.TestOrderMapper;
import com.cms.item.service.TestOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


/**
 * // 参考文章：http://javadaily.cn/articles/2019/12/19/1576731515587.html
 * // https://blog.csdn.net/qq853632587/article/details/111644295
 * // https://www.cnblogs.com/haoxianrui/p/14280184.html
 * @author ydf Created by 2021/12/22 12:42
 */
@Service
public class TestOrderServiceImpl extends ServiceImpl<TestOrderMapper, TestOrderEntity> implements TestOrderService {

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public ResultUtil<?> insert(Integer a) {
        TestOrderEntity orderEntity = new TestOrderEntity();
        orderEntity.setA(a);
        orderEntity.setB(a);
        this.baseMapper.insert(orderEntity);

//        try {
//            String[] arr = {"1","2","3"};
//            // 抛出异常
//            s = arr[10];
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultUtil.error("事务异常回滚");
//        }
        String[] arr = {"1","2","3"};
        // 抛出异常 如果try/catch 会导致事务失效
        String s = arr[10];
       return ResultUtil.success("添加成功");
    }
}
