package cn.pjx.springlite.aop.framework;

import cn.pjx.springlite.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK静态代理实现类
 * - 基于JDK实现的代理类,需要实现接口AopProxy,InvocationHandler, 这样就可以把代理对象getProxy和反射调用方法invoke分开处理了
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 代理一个对象,根据ClassLoader,AdvisedSupport和当前这个类this,因为这个类提供了invoke方法
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }

    /**
     * invoke方法中主要处理匹配的方法后,使用用户自己提供的方法拦截实现,做反射调用methodInterceptor.invoke
     * - 按照目前的理解, 所有目标对象的方法调用真实路径都会走这个方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果类和方法匹配后在切点范围的话, 就走用户自定义的MethodInterceptor#invoke代理方法
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        // 否则直接调用对象的原生方法即可
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }
}
