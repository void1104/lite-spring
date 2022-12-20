package cn.pjx.springlite.context.annotation;


import cn.hutool.core.util.ClassUtil;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 类路径Component扫描类
 */
public class ClassPathScanningCandidateComponentProvider {

    /**
     * 找到所有打上了@Component注解的类
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        // TODO 这里beanDefinition的初始化会少了很多信息
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
