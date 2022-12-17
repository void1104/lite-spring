package cn.pjx.springlite.aop;

/**
 * 切入点接口
 */
public interface PointCut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
