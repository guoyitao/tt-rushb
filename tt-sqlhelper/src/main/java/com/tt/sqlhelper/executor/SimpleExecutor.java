package com.tt.sqlhelper.executor;

import com.tt.sqlhelper.constants.Constant;
import com.tt.sqlhelper.executor.parameter.DefaultParameterHandler;
import com.tt.sqlhelper.executor.parameter.ParameterHandler;
import com.tt.sqlhelper.executor.result.DefaultResultSetHandler;
import com.tt.sqlhelper.executor.result.ResultSetHandler;
import com.tt.sqlhelper.executor.statement.SimpleStatmentHandler;
import com.tt.sqlhelper.executor.statement.StatmentHandler;
import com.tt.sqlhelper.mapping.BoundSql;
import com.tt.sqlhelper.mapping.MappedStatement;
import com.tt.sqlhelper.session.Configuration;

import java.sql.*;
import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/23 21:45
 * @version: 1.0
 */
public class SimpleExecutor implements Executor {

    private static final String url;

    private static final String username;

    private static final String password;

    static {
        String driver = Configuration.getProperty(Constant.DB_DRIVER_CONF);
        url = Configuration.getProperty(Constant.DB_URL_CONF);
        username = Configuration.getProperty(Constant.DB_USERNAME_CONF);
        password = Configuration.getProperty(Constant.db_PASSWORD);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库连接
     */


    private Configuration conf;

    public SimpleExecutor(Configuration configuration) {
        this.conf = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        try {
            Connection connection = getConnection();

            BoundSql boundSql = ms.getSqlSource().getBoundSql(parameter);

            StatmentHandler statementHandler = new SimpleStatmentHandler(ms,boundSql);
//        预编译sql
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

//        参数handler
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter, ms,boundSql);
//        将SQL语句中?参数化
            parameterHandler.setParameters(preparedStatement);

            //执行SQL，得到结果集ResultSet
            ResultSet resultSet = statementHandler.query(preparedStatement);

//      把查询结果设置到resultType
            ResultSetHandler resultSetHandler = new DefaultResultSetHandler(ms);

            return resultSetHandler.handleResultSets(resultSet);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        Connection connection = getConnection();

        BoundSql boundSql = ms.getSqlSource().getBoundSql(parameter);

        StatmentHandler statementHandler = new SimpleStatmentHandler(ms, boundSql);
//        预编译sql
        PreparedStatement preparedStatement = statementHandler.prepare(connection);

//        参数handler
        ParameterHandler parameterHandler = new DefaultParameterHandler(parameter, ms,boundSql);
//        将SQL语句中?参数化
        parameterHandler.setParameters(preparedStatement);

        //执行SQL，得到结果集ResultSet
        int row = statementHandler.update(preparedStatement);

        return row;
    }


    private Connection getConnection() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("获得连接");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
