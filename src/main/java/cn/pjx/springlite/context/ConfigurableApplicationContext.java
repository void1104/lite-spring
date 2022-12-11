package cn.pjx.springlite.context;

import cn.pjx.springlite.beans.BeanException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     */
    void refresh() throws BeanException;
}
