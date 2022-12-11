package cn.pjx.springlite.common;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if (beanName.equals("userService")) {
            System.out.println("this is:[" + beanName + "] before action!!");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        if (beanName.equals("userService")) {
            System.out.println("this is:[" + beanName + "] after action!!");
        }
        return bean;
    }
}
