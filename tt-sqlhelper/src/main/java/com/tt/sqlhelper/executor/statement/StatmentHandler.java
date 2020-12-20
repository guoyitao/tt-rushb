package com.tt.sqlhelper.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: guoyitao
 * @date: 2020/11/24 15:45
 * @version: 1.0
 */
public interface StatmentHandler {

    /**
     * SQL预处理
     *
     * @param paramConnection
     * @return
     * @throws SQLException
     * @see
     */
    PreparedStatement prepare(Connection paramConnection) throws SQLException;

    /**
     * 查询数据库
     *
     * @param preparedStatement
     * @return
     * @throws SQLException
     * @see
     */
    ResultSet query(PreparedStatement preparedStatement) throws SQLException;

    /**
     * update
     *
     * @param preparedStatement
     * @throws SQLException
     */
    int update(PreparedStatement preparedStatement) throws SQLException;

}
