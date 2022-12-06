package cn.pjx.springlite.beans.factory.config;

/**
 * bean定义注册中心接口
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册bean定义
     *
     * @param beanName       bean名称
     * @param beanDefinition bean定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断是否包含指定名称的BeanDefinition
     *
     * @param beanName bean名称
     * @return bean定义是否存在
     */
    boolean containsBeanDefinition(String beanName);
}
