package com.tt.sqlhelper.annotations;

import java.lang.annotation.*;

/**
 * @author: guoyitao
 * @date: 2020/11/24 22:26
 * @version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    /**
     * Returns the parameter name.
     *
     * @return the parameter name
     */
    String value();
}