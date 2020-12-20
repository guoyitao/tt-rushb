package com.tt.sqlhelper.executor.result;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/24 15:47
 * @version: 1.0
 */
public interface ResultSetHandler {
    <E> List<E> handleResultSets(ResultSet resultSet);
}
