package com.tt.sqlhelper.executor.parameter;

import java.sql.PreparedStatement;

/**
 * @author: guoyitao
 * @date: 2020/11/24 15:39
 * @version: 1.0
 */
public interface ParameterHandler {
    /**
     * 设置参数
     *
     * @param paramPreparedStatement
     * @see
     */
    void setParameters(PreparedStatement paramPreparedStatement);
}
