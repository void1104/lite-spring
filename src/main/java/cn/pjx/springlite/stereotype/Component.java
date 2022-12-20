package cn.pjx.springlite.stereotype;

import java.lang.annotation.*;

/**
 * 标识SpringBean的注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
