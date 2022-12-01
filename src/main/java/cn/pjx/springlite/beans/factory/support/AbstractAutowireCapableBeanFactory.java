package cn.pjx.springlite.beans.factory.support;

import cn.pjx.springlite.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }

        // 添加单例
        addSingleton(beanName, bean);
        return bean;
    }
}
