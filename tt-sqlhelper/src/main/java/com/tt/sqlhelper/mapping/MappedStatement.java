package com.tt.sqlhelper.mapping;

import com.tt.sqlhelper.constants.Constant;
import com.tt.sqlhelper.constants.Constant.SqlType;

/**
 * @author: guoyitao
 * @date: 2020/11/23 21:25
 * @version: 1.0
 */
public class MappedStatement {

    /** mapper文件的namespace */
    private String namespace;

    /** sql的id属性 */
    private String sqlId;

    /** sql语句，对应源码的sqlSource */
    private String sql;
    private SqlSource sqlSource;

    /** 返回类型 */
    private String resultType;

    /** 入参类型*/
    private String parameterType;

    /** sqlCommandType对应select/update/insert等 */
    private SqlType sqlCommandType;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public SqlType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    @Override
    public String toString() {
        return "MappedStatement{" + "namespace='" + namespace + '\'' + ", sqlId='" + sqlId + '\'' + ", sql='" + sql + '\'' + ", sqlSource=" + sqlSource + ", resultType='" + resultType + '\'' + ", parameterType='" + parameterType + '\'' + ", sqlCommandType=" + sqlCommandType + '}';
    }
}
