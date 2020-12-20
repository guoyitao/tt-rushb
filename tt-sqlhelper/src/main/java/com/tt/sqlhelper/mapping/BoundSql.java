package com.tt.sqlhelper.mapping;

import com.tt.sqlhelper.session.Configuration;

import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/24 19:35
 * @version: 1.0
 */
public class BoundSql {


    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;

    public BoundSql(String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public void setParameterObject(Object parameterObject) {
        this.parameterObject = parameterObject;
    }
}
