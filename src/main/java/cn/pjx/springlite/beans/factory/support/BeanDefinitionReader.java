package cn.pjx.springlite.beans.factory.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.config.BeanDefinitionRegistry;
import cn.pjx.springlite.core.io.Resource;
import cn.pjx.springlite.core.io.ResourceLoader;

/**
 * Bean定义读取器
 */
public interface BeanDefinitionReader {

    /**
     * 获取bean注册中心
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     */
    ResourceLoader getResourceLoader();

    /**
     * 从数据源加载bean定义数据
     */
    void loadBeanDefinitions(Resource resource) throws BeanException;

    /**
     * 从多个数据源加载bean定义数据
     */
    void loadBeanDefinitions(Resource... resources) throws BeanException;

    /**
     * 从指定location加载bean定义数据
     */
    void loadBeanDefinitions(String location) throws BeanException;

    /**
     * 从多个location加载bean定义数据
     */
    void loadBeanDefinitions(String... locations) throws BeanException;
}
