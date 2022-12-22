package cn.pjx.springlite.aop;

import cn.pjx.springlite.util.ClassUtils;

/**
 * 被代理的目标对象封装类
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取bean实例的类型
     *
     * @return bean实例
     */
    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();
        // 对于cglib来说,如果是cglib代理对象, 其父类Class才是原生Class
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
