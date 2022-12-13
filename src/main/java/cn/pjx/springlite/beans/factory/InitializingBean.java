package cn.pjx.springlite.beans.factory;

/**
 * 定义了初始化方法的接口
 */
public interface InitializingBean {

    /**
     * 当Bean处理了属性填充后调用
     */
    void afterPropertiesSet() throws Exception;
}
