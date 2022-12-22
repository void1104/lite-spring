package cn.pjx.springlite.beans.factory.config;

import cn.pjx.springlite.beans.factory.BeanFactory;
import cn.pjx.springlite.util.StringValueResolver;

public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加bean在spring容器中实例化前后的processor
     *
     * @param beanPostProcessor beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 为嵌入的值(如注解属性)添加一个字符串解析器
     *
     * @param valueResolver 字符串解析器
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 解析获得注解属性中占位符的实际值
     *
     * @param value value
     */
    String resolveEmbeddedValue(String value);
}
