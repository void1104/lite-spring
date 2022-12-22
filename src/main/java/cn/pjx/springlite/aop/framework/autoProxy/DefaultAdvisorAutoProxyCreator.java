package cn.pjx.springlite.aop.framework.autoProxy;

import cn.pjx.springlite.aop.*;
import cn.pjx.springlite.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.pjx.springlite.aop.framework.ProxyFactory;
import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.PropertyValues;
import cn.pjx.springlite.beans.factory.BeanFactory;
import cn.pjx.springlite.beans.factory.BeanFactoryAware;
import cn.pjx.springlite.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.pjx.springlite.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * 融入Bean生命周期的自动代理创建者
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }

    /**
     * 判断是不是aop模块涉及的基础类
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        // aop模块的基础类,则不需要执行代理逻辑
        if (isInfrastructureClass(bean.getClass()))
            return bean;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 只有当切入点与当前beanClass匹配, 才执行代理逻辑
            if (!classFilter.matches(bean.getClass()))
                continue;

            // 组装切点和通知等切面信息, 用于生成代理对象
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            // 返回代理对象 TODO 这里有个问题,如果有多个切面,这里只能被增强一次
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean) throws BeanException {
        return pvs;
    }
}
