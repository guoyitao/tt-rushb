package com.tt.sqlhelper.mapping;

import com.tt.sqlhelper.session.Configuration;

import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/24 19:36
 * @version: 1.0
 */
public class MySqlSourse implements SqlSource {

    private final String sql;
    private final List<ParameterMapping> parameterMappings;
    private final Configuration configuration;

    public MySqlSourse(Configuration configuration, String sql) {
        this(sql,null,configuration);
    }

    public MySqlSourse(String sql, List<ParameterMapping> parameterMappings, Configuration configuration) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(sql, parameterMappings, parameterObject);
    }

    @Override
    public String toString() {
        return "MySqlSourse{" + "sql='" + sql + '\'' + ", parameterMappings=" + parameterMappings+'}';
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
