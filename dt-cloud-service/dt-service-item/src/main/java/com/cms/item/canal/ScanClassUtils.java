package com.cms.item.canal;

import com.cms.item.canal.annotation.Canal;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 扫描java包下带有注解的子类
 * @author ydf Created by 2021/10/11 16:36
 */
public class ScanClassUtils {

    public static Set<Class<?>> scanClass(String classPath, Class<? extends Annotation> annotation) {
        Set<Class<?>> classList = new HashSet<>();
        if (ObjectUtils.isEmpty(classPath)) {
            return classList;
        }
        // true：默认TypeFilter生效，这种模式会查询出许多不符合你要求的class名
        // false：关闭默认TypeFilter
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        // 自定义过滤器(这是扫描全部,但不能扫描出接口)
        TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> true;
        provider.addIncludeFilter(includeFilter);
        // 指定扫描的包名
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(classPath);
        Set<BeanDefinition> beanDefinitionSet = new HashSet<>(candidateComponents);
        beanDefinitionSet.forEach(beanDefinition -> {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                if (!ObjectUtils.isEmpty(annotation)) {
                    if (!ObjectUtils.isEmpty(AnnotationUtils.getAnnotation(clazz, annotation))) {
                        classList.add(clazz);
                    }
                } else {
                    classList.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return classList;
    }

    public static void main(String[] args) {
        // [class com.cms.item.canal.processer.EmployeeProcess, class com.cms.item.canal.processer.AccountProcess]
        Set<Class<?>> classList = scanClass("com.cms.item.canal.processer", Canal.class);
        System.out.println(classList);
    }
}
