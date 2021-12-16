package com.cms.item.canal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Canal同步表注解自定义
 * ElementType.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 * ElementType.METHOD:用于描述方法
 * @author DT
 * @date 2021/11/20 15:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Canal {

    /**
     * 操作的表名称
     */
    String table();
}
