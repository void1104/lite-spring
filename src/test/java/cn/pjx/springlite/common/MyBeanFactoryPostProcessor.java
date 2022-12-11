package cn.pjx.springlite.common;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.PropertyValue;
import cn.pjx.springlite.beans.PropertyValues;
import cn.pjx.springlite.beans.factory.ConfigurableListableBeanFactory;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("username", "void"));

    }
}
