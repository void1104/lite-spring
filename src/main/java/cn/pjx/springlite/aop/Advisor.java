package cn.pjx.springlite.aop;

import org.aopalliance.aop.Advice;

public interface Advisor {

    /**
     * Return the advice part of this aspect.
     * An advice may be an interceptor, a before advice, a throws advice, etc.
     * @return the advice that should apply if the pointcut matches.
     */
    Advice getAdvice();
}
