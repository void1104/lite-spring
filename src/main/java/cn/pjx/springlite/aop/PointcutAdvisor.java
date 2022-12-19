package cn.pjx.springlite.aop;

/**
 * PointcutAdvisor承担了Pointcut和Advice的组合.
 * Pointcut用于获取JoinPoint.
 * Advice决定于JoinPoint执行什么操作.
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * Get the Pointcut that drives this advisor
     */
    Pointcut getPointcut();
}
