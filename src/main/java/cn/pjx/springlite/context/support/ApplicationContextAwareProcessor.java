package cn.pjx.springlite.context.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.config.BeanPostProcessor;
import cn.pjx.springlite.context.ApplicationContext;
import cn.pjx.springlite.context.ApplicationContextAware;

/**
 * ApplicationContext无法在创建bean时拿到,所以要在refresh时,把ApplicationContext写入到一个包装的BeanPostProcessor中
 * 再由AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization方法调用
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
