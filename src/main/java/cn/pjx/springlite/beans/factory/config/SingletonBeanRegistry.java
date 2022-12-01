package cn.pjx.springlite.beans.factory.config;

/**
 * 实例注册中心接口
 */
public interface SingletonBeanRegistry {

    /**
     * 获取bean实例
     *
     * @param beanName bean注册时的名称
     * @return bean实例对象
     */
    Object getSingleton(String beanName);
}
