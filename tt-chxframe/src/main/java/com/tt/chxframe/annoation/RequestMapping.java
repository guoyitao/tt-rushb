package com.tt.chxframe.annoation;

import java.lang.annotation.*;

/**
 * 请求url
 * @author: guoyitao
 * @date: 2020/11/28 16:45
 * @version: 1.0
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
