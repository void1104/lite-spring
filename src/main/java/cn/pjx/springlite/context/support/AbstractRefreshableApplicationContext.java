package cn.pjx.springlite.context.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.ConfigurableListableBeanFactory;
import cn.pjx.springlite.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory tmpBeanFactory = new DefaultListableBeanFactory();
        loadBeanDefinitions(tmpBeanFactory);
        this.beanFactory = tmpBeanFactory;
    }

    /**
     * 此类只负责封装和初始化beanFactory，具体bean定义如何读取，交给子类负责.
     *
     * @param beanFactory beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
