package com.tt.sqlhelper.binding;

import com.tt.sqlhelper.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: guoyitao
 * @date: 2020/11/23 21:24
 * @version: 1.0
 */
public class MapperRegistry {

    /** the knownMappers */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    /**
     * 添加mapper 并创建代理工厂
     * @param type 需要创建代理工厂的接口
     * @return void
     */
    public <T> void addMapper(Class<T> type) {
        this.knownMappers.put(type,new MapperProxyFactory<>(type));
    }

    /**
     *  获取代理工厂 通过工厂创建代理对象
     * @param type 要创建的mapper接口
     * @param sqlSession  sqlsession
     * @return T
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<?> mapperProxyFactory = this.knownMappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }
}
