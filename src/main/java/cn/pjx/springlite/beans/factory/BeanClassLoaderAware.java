package cn.pjx.springlite.beans.factory;

/**
 * BeanClassLoader感知类接口,实现此接口即能感知到所属的BeanClassLoader
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);
}
