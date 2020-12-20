package com.tt.chxframe.annoation;

import java.lang.annotation.*;

/**
 * 业务逻辑,注入接口
 * @author: guoyitao
 * @date: 2020/11/28 16:45
 * @version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
