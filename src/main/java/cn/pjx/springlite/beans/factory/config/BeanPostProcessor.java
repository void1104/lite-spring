package cn.pjx.springlite.beans.factory.config;

import cn.pjx.springlite.beans.BeanException;

/**
 * 用于在调用初始化方法前(这里指用户实现InitializingBean后的自定义初始化方法, 并不是构造函数),修改实例化Bean对象
 */
public interface BeanPostProcessor {

    /**
     * 在Bean对象执行初始化方法之前，执行此方法
     *
     * @param bean     要修改的bean
     * @param beanName 要修改的beanName
     * @return 扩展后的bean
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    /**
     * 在Bean对象执行初始化方法之后，执行此方法
     *
     * @param bean     要修改的bean
     * @param beanName 要修改的beanName
     * @return 扩展后的bean
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;
}
