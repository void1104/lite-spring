package cn.pjx.springlite.aop;

import java.lang.reflect.Method;

/**
 * 方法前置通知
 * - 在spring框架中, Advice都是通过方法拦截器MethodInterceptor实现的.
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * 在目标对象的方法被调用前,会走这个方法
     *
     * @param method 方法
     * @param args   参数
     * @param target 目标对象
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
