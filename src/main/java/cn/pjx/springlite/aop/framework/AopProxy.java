package cn.pjx.springlite.aop.framework;

/**
 * 标准接口,用于获取代理类,具体实现方式可以有JDK方式,也可以是Cglib方式.
 */
public interface AopProxy {

    /**
     * 获取代理类
     */
    Object getProxy();
}
