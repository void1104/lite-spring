package cn.pjx.springlite.beans.factory.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.BeanFactory;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;

/**
 * bean工厂抽象类
 * <p>
 * - 仅负责获取bean的能力
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeanException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeanException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return (T) getBean(name);
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
