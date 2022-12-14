package cn.pjx.springlite.beans.factory;

import cn.pjx.springlite.beans.BeanException;

/**
 * BeanFactory感知类接口,实现此接口即能感知到所属的BeanFactory
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
