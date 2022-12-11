package cn.pjx.springlite.beans.factory;

import cn.pjx.springlite.beans.BeanException;

import java.util.Map;

/**
 * 批量获取bean属性的工厂规范
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按照类型返回 Bean 实例
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeanException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

    /**
     * 返回bean定义注册表中的所有bean名称
     *
     * @return list
     */
    String[] getBeanDefinitionNames();
}
