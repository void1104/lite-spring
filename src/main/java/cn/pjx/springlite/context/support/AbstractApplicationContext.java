package cn.pjx.springlite.context.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.ConfigurableListableBeanFactory;
import cn.pjx.springlite.beans.factory.config.BeanFactoryPostProcessor;
import cn.pjx.springlite.beans.factory.config.BeanPostProcessor;
import cn.pjx.springlite.context.ApplicationEvent;
import cn.pjx.springlite.context.ApplicationListener;
import cn.pjx.springlite.context.ConfigurableApplicationContext;
import cn.pjx.springlite.context.event.ApplicationEventMulticaster;
import cn.pjx.springlite.context.event.ContextClosedEvent;
import cn.pjx.springlite.context.event.ContextRefreshedEvent;
import cn.pjx.springlite.context.event.SimpleApplicationEventMulticaster;
import cn.pjx.springlite.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象应用上下文
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeanException {
        // 1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();

        // 2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3.添加ApplicationContextAwareProcessor, 让继承自ApplicationContextAware的Bean对象都能感知所属的ApplicationContext.
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4.在bean实例化前，调用BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5.BeanPostProcessor需要提前于其他Bean,对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 6.初始化事件发布者
        initApplicationEventMulticaster();

        // 7.注册事件监听器
        registerListeners();

        // 8.容器启动时，(预加载)提前实例化全部单例对象
        beanFactory.preInstantiateSingletons();

        // 9.发布容器刷新完成事件.
        finishRefresh();
    }

    protected abstract void refreshBeanFactory() throws BeanException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 用户可以自己实现BeanFactoryPostProcessor接口，拿到spring对外暴露的工厂 就可以对spring中所有bean定义进行一层过滤修改
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

    /**
     * 初始化事件发布者
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        // 注册广播器的单例
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 注册定义的事件监听器
     */
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 容器完成刷新/初始化后的动作
     */
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }
}
