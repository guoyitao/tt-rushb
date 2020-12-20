package com.tt.sqlhelper.mapping;

/**
 * @author: guoyitao
 * @date: 2020/11/24 19:35
 * @version: 1.0
 */
public interface SqlSource {

    BoundSql getBoundSql(Object parameterObject);
}
