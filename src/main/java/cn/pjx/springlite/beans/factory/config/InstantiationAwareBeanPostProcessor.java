package cn.pjx.springlite.beans.factory.config;

import cn.pjx.springlite.beans.BeanException;

/**
 * bean实例化感知postProcessor
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;
}
