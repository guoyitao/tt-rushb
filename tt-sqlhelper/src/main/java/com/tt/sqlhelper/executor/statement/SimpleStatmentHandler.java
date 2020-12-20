package com.tt.sqlhelper.executor.statement;

import com.tt.sqlhelper.mapping.BoundSql;
import com.tt.sqlhelper.mapping.MappedStatement;
import com.tt.sqlhelper.util.CommonUtil;
import com.tt.sqlhelper.util.RegexUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: guoyitao
 * @date: 2020/11/24 15:34
 * @version: 1.0
 */
public class SimpleStatmentHandler implements StatmentHandler {

    private MappedStatement mappedStatement;
    private BoundSql boundSql;

    public SimpleStatmentHandler(MappedStatement mappedStatement, BoundSql boundSql) {
        this.mappedStatement = mappedStatement;
        this.boundSql = boundSql;
    }

    @Override
    public PreparedStatement prepare(Connection paramConnection) throws SQLException {
        String originalSql = boundSql.getSql();

        if (CommonUtil.isNotEmpty(originalSql)){
            return paramConnection.prepareStatement(parseSymbo(originalSql));
        }else{
            throw new RuntimeException(new SQLException("original sql is null."));
        }

    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    @Override
    public int update(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }


    /**
     *  将SQL语句中的#{}替换为？，源码中是在SqlSourceBuilder类中解析的
     * @param sql
     * @return java.lang.String
     */
    private static String parseSymbo(String sql){
        sql = CommonUtil.stringTrim(sql);
        Matcher matcher = RegexUtil.param_pattern.matcher(sql);
        return matcher.replaceAll("?");
    }
}
