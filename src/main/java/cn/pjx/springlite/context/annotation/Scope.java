package cn.pjx.springlite.context.annotation;

import java.lang.annotation.*;

/**
 * 对象域注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";
}
