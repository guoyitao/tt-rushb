package com.tt.chxframe.annoation;

import java.lang.annotation.*;

/**
 * 请求参数映射
 * @author: guoyitao
 * @date: 2020/11/28 16:45
 * @version: 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}
