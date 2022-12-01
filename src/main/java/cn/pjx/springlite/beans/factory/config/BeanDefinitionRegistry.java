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
}
