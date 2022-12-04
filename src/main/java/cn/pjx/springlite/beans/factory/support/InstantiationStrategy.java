package cn.pjx.springlite.beans.factory.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略接口
 */
public interface InstantiationStrategy {

    /**
     * 实例化bean
     *
     * @param beanDefinition bean定义
     * @param ctor           实例化构造器
     * @param args           构造函数参数
     * @return 实例化bean
     */
    Object instantiate(BeanDefinition beanDefinition, Constructor ctor, Object[] args) throws BeanException;
}
