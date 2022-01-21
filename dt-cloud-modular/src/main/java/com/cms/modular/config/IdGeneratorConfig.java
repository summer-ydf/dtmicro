package com.cms.modular.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * 雪花算法主键生成策略配置
 * @author ydf Created by 2022/1/21 10:09
 */
public class IdGeneratorConfig implements IdentifierGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGeneratorConfig.class);

    public final IdGeneratorOptions options = new IdGeneratorOptions((short) 1);

    @PostConstruct
    public void init() {
        logger.info("初始化分布式主键生成策略============================");
        YitIdHelper.setIdGenerator(options);
    }

    @Override
    public Long nextId(Object entity) {
        return YitIdHelper.nextId();
    }
}
