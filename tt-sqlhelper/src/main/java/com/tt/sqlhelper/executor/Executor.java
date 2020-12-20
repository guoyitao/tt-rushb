package com.tt.sqlhelper.executor;

import com.tt.sqlhelper.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/23 21:44
 * @version: 1.0
 */
public interface Executor {


    <E> List<E> query(MappedStatement ms, Object parameter)throws SQLException;

    int update(MappedStatement ms, Object parameter)throws SQLException;

}
