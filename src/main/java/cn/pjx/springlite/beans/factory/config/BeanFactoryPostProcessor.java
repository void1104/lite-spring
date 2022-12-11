package cn.pjx.springlite.beans.factory.config;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.ConfigurableListableBeanFactory;

/**
 * 允许在bean实例化之前自定义修改BeanDefinition属性信息
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的BeanDefinition加载完成后，实例化Bean对象之前，提供修改BeanDefinition属性的机制.
     *
     * @param beanFactory beanFactory
     * @throws BeanException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException;
}
