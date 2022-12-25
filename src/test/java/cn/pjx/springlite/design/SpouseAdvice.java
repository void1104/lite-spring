package cn.pjx.springlite.design;

import cn.pjx.springlite.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author pengjiaxin3
 * @description
 * @date 12/25/22 12:58 PM
 */
public class SpouseAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("SpouseAdive advice!!! " + method);
    }
}
