package com.cms.provider.config.base;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


/**
 * 雪花算法主键生成策略配置
 * @author ydf Created by 2022/1/21 10:09
 */
@Configuration
public class IdGeneratorConfig implements IdentifierGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGeneratorConfig.class);

    @PostConstruct
    public void init() {
        logger.info("初始化分布式主键生成策略============================");
        IdGenerator.initDefaultInstance(1);
    }

    @Override
    public Long nextId(Object entity) {
        return IdGenerator.generateId();
    }
}
