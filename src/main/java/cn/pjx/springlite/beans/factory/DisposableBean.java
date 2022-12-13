package cn.pjx.springlite.beans.factory;

/**
 * 定义了销毁方法的接口
 */
public interface DisposableBean {

    /**
     * Bean销毁时调用
     */
    void destroy() throws Exception;
}
