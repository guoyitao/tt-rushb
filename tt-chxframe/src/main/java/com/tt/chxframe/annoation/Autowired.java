package com.tt.chxframe.annoation;

import java.lang.annotation.*;

/**
 * 自动注入
 * @author: guoyitao
 * @date: 2020/11/28 16:44
 * @version: 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
