package cn.pjx.springlite.beans.factory.support;

import cn.pjx.springlite.beans.factory.BeanFactory;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;

/**
 * bean工厂抽象类
 * <p>
 * - 仅负责获取bean的能力
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return doGetBean(name, args);
    }

    protected Object doGetBean(String name, final Object[] args) {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String name);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);
}
