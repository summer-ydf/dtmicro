package com.sku.api.factory;

import com.cms.common.tool.result.ResultUtil;
import com.sku.api.feign.MallSkuFeignService;
import feign.hystrix.FallbackFactory;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 熔断工厂
 * @author ydf Created by 2021/11/23 16:05
 */
@Component
public class MallSkuFeignClientFallback implements FallbackFactory<MallSkuFeignService> {

    Logger log = LoggerFactory.getLogger(MallSkuFeignClientFallback.class);

    @Override
    public MallSkuFeignService create(Throwable throwable) {
        log.info("调用接口请求出错：{}",throwable.getMessage());
        return new MallSkuFeignService() {

            @Override
            public ResultUtil<?> reduceStock(Integer id) {
                log.info("=============FILE START=================");
                log.info("当前 XID: {}", RootContext.getXID());
                try {
                    GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                    log.info("=============FILE END=================");
                } catch (TransactionException ex) {
                    ex.printStackTrace();
                }
                return ResultUtil.error("扣减库存失败");
            }
        };
    }
}
