package cn.pjx.springlite.aop.bean;

import cn.pjx.springlite.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author pengjiaxin3
 * @description 自定义拦截方法
 * @date 12/19/22 5:02 PM
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法: " + method.getName());
    }
}
