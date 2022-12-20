package cn.pjx.springlite.context.annotation;

import cn.hutool.core.util.StrUtil;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.beans.factory.config.BeanDefinitionRegistry;
import cn.pjx.springlite.stereotype.Component;

import java.util.Set;

/**
 * 类路径BeanDefinition扫描类
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析Bean的作用域singleton,prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                // 将扫描到的类注册到beanDefinition的注册中心
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    /**
     * 解析获取@Scope注解里的值
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope)
            return scope.value();
        return StrUtil.EMPTY;
    }

    /**
     * 解析获取@Component注册bean的beanName,默认为类名首字母小写.
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
