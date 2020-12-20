package com.tt.chxframe.annoation;

import java.lang.annotation.*;

/**
 * 访问数据库层，注入接口
 * @author: guoyitao
 * @date: 2020/11/28 16:45
 * @version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dao {
    String value() default "";
}
