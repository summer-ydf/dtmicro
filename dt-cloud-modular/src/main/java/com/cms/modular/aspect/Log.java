package com.cms.modular.aspect;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解自定义
 * ElementType.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 * ElementType.METHOD:用于描述方法
 * @author DT
 * @date 2021/11/20 15:18
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 日志操作模块名称
     */
    String title() default "";

    /**
     * 日志处理模块类型（新增、删除、更新）
     */
    String businessType() default LogTypeCode.OTHER;

}
