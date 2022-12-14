package cn.pjx.springlite.beans.factory;

/**
 * BeanName感知类接口,实现此接口就能感知到所属的BeanName
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String name);
}
