package com.user.api.factory;


import com.user.api.feign.MallUserFeignService;
import feign.hystrix.FallbackFactory;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断工厂
 * @author DT辰白 Created by 2021/11/23 16:05
 */
@Component
public class MallUserFeignClientFallback implements FallbackFactory<MallUserFeignService> {

    Logger log = LoggerFactory.getLogger(MallUserFeignClientFallback.class);

    @Override
    public MallUserFeignService create(Throwable throwable) {
        log.info("调用接口请求出错：{}",throwable.getMessage());
        return new MallUserFeignService() {
            @Override
            public void reduceMoney(Integer id) {
                log.info("=============FILE START=================");
                log.info("当前 XID: {}", RootContext.getXID());
                try {
                    GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                    log.info("=============FILE END=================");
                } catch (TransactionException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
}
