package cn.pjx.springlite.context.support;

import cn.pjx.springlite.beans.BeanException;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private final String[] configLocations;

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文(初始化容器)
     *
     * @param configLocations
     * @throws BeanException
     */
    public ClassPathXmlApplicationContext(String configLocations) throws BeanException {
        this(new String[]{configLocations});
    }

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文(初始化容器)
     *
     * @param configLocations
     * @throws BeanException
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeanException {
        this.configLocations = configLocations;
        // 刷新(初始化)容器
        refresh();
        // 注册JVM钩子
        registerShutdownHook();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
