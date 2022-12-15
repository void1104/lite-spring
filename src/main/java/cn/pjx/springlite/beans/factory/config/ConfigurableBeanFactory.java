package cn.pjx.springlite.beans.factory.config;

import cn.pjx.springlite.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加bean在spring容器中实例化前后的processor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
