package cn.pjx.springlite.beans.factory;

import cn.pjx.springlite.beans.BeanException;

/**
 * bean工厂接口
 */
public interface BeanFactory {

    /**
     * 获取bean实例
     *
     * @param name bean注册名称
     * @return bean实例
     */
    Object getBean(String name) throws BeanException;

    /**
     * 获取bean实例
     *
     * @param name bean注册名称
     * @param args 构造函数入参
     * @return bean实例
     */
    Object getBean(String name, Object... args) throws BeanException;

    /**
     * 获取bean实例<泛型>
     *
     * @param name         bean注册名称
     * @param requiredType 泛型类型
     * @return bean实例
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeanException;
}
