package cn.pjx.springlite.context;

import cn.pjx.springlite.beans.BeanException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     */
    void refresh() throws BeanException;

    /**
     * 注册JVM停止时的钩子函数
     */
    void registerShutdownHook();

    /**
     * 关闭容器
     */
    void close();
}
