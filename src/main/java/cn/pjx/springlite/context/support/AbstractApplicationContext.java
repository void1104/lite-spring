package cn.pjx.springlite.context.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.ConfigurableListableBeanFactory;
import cn.pjx.springlite.beans.factory.config.BeanFactoryPostProcessor;
import cn.pjx.springlite.beans.factory.config.BeanPostProcessor;
import cn.pjx.springlite.context.ConfigurableApplicationContext;
import cn.pjx.springlite.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * 抽象应用上下文
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeanException {
        // 1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();

        // 2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3.在bean实例化前，调用BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4.BeanPostProcessor需要提前于其他Bean,对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 5.提前实例化单例对象
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeanException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 用户可以自己实现BeanFactoryPostProcessor接口，拿到spring对外暴露的工厂
     * 就可以对spring中所有bean定义进行一层过滤修改
     *
     * @param beanFactory beanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor processor : beanFactoryPostProcessorMap.values()) {
            processor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 将BeanPostProcessor注册到AbstractBeanFactory中，bean实例化时会调用其beforeXXX和afterXXX方法
     *
     * @param beanFactory beanFactory
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor processor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(processor);
        }
    }

    @Override
    public Object getBean(String name) throws BeanException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeanException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
