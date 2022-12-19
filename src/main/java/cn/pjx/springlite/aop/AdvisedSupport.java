package cn.pjx.springlite.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 切面织入包装类 - 把代理,拦截,匹配的各项属性包装到一个类中,方便在Proxy实现类中进行使用.
 */
public class AdvisedSupport {

    // false:JDK动态代理, true:Cglib动态代理
    private boolean proxyTargetClass = false;

    // 被代理的目标对象
    private TargetSource targetSource;

    // 方法拦截器 / 方法增强器 (拦截方法实现类, 由用户自己实现invoke方法做增强逻辑)
    private MethodInterceptor methodInterceptor;

    // 方法匹配器(检查目标方法是否符合切入条件)
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
