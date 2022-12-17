package cn.pjx.springlite.aop;

import java.lang.reflect.Method;

/**
 * Method匹配器,找到表达式范围内的目标类和方法
 */
public interface MethodMatcher {

    /**
     * 当前方法和class是否在切点表达式范围内.
     */
    boolean matches(Method method, Class<?> targetClass);
}
