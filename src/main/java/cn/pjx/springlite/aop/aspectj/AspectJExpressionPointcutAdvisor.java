package cn.pjx.springlite.aop.aspectj;

import cn.pjx.springlite.aop.Pointcut;
import cn.pjx.springlite.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 把切面pointcut,拦截方法advice和具体的拦截表达式包装在一起,这样就可以在xml的配置中定义一个pointcutAdvisor
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切面
    private AspectJExpressionPointcut pointcut;

    // 具体的拦截方法
    private Advice advice;

    // 表达式
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
