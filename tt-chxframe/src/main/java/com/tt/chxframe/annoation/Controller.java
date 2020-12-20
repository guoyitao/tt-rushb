package com.tt.chxframe.annoation;

import java.lang.annotation.*;

/**
 * 页面交互
 * @author: guoyitao
 * @date: 2020/11/28 16:44
 * @version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
