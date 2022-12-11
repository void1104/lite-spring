package cn.pjx.springlite.beans.factory;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.beans.factory.config.BeanPostProcessor;
import cn.pjx.springlite.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    void preInstantiateSingletons() throws BeanException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
