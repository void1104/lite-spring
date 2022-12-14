package cn.pjx.springlite.context;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.Aware;

/**
 * 实现此接口,即能感知到所属的ApplicationContext
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
