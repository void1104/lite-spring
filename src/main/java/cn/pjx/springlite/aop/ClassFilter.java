package cn.pjx.springlite.aop;

/**
 * Class匹配器,用于切点找到给定的接口和目标类
 */
public interface ClassFilter {

    /**
     * 判断当前类是否为切点匹配的类
     */
    boolean matches(Class<?> clazz);
}
