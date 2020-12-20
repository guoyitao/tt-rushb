package com.tt.sqlhelper.session.defaults;

import com.tt.sqlhelper.executor.Executor;
import com.tt.sqlhelper.executor.SimpleExecutor;
import com.tt.sqlhelper.mapping.MappedStatement;
import com.tt.sqlhelper.session.Configuration;
import com.tt.sqlhelper.session.SqlSession;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: guoyitao
 * @date: 2020/11/23 21:43
 * @version: 1.0
 */
public class DefaultSqlSession implements SqlSession {


    private final Configuration configuration;

    private final Executor executor;


    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
    }


    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        List<T> objects = selectList(statementId, parameter);
        return objects.get(0);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter)  {
        try {
            MappedStatement ms = configuration.getMappedStatement(statementId);
            return executor.query(ms,parameter);
        } catch (Exception throwables) {
            throw  new RuntimeException(throwables);
        }
    }

    @Override
    public int update(String statementId, Object parameter) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statementId);
            return executor.update(ms,parameter);
        } catch (Exception throwables) {
            throw  new RuntimeException(throwables);
        }

    }

    @Override
    public int insert(String statementId, Object parameter) {
        return update(statementId,parameter);
    }

    @Override
    public int delete(String statementId, Object parameter) {
        return update(statementId,parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    /**
     *  把对象包装成map
     *  key   属性名
     *  value 值
     * @param object
     * @return java.lang.Object
     */
    private Object wrapObjectToMapIfCollection (Object object) {
        if (object instanceof Collection) {
            Map<String, Object> map = new HashMap<>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            return map;
        }else if (object != null && object.getClass().isArray()){
            Map<String, Object> map = new HashMap<>();
            map.put("array", object);
            return map;
        }
        return object;
    }
}
