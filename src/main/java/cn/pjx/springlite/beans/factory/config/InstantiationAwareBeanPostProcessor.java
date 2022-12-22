package cn.pjx.springlite.beans.factory.config;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.PropertyValues;

/**
 * bean实例化感知postProcessor
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param beanClass beanClass
     * @param beanName  beanName
     * @return 经过处理(or代理)后的bean
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean     bean
     * @param beanName beanName
     * @return success or not
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException;

    /**
     * 在 Bean 对象实例化完成后, 设置属性操作之前执行此方法
     *
     * @param pvs  bean的成员变量kvs
     * @param bean bean
     * @return 经处理过成员变量kvs
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean) throws BeanException;
}
