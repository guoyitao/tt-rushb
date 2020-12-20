package com.tt.sqlhelper.binding;

import com.tt.sqlhelper.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author: guoyitao
 * @date: 2020/11/24 13:07
 * @version: 1.0
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     *  根据sqlSession创建一个代理mapper
     * @param sqlSession
     * @return T
     */
    public <T> T newInstance(SqlSession sqlSession) {
        MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, (Class<T>) this.mapperInterface);
        return newInstance(mapperProxy);
    }

    /**
     *  jdk动态代理
     * @param tMapperProxy InvocationHandler
     * @return T
     */
    private <T> T newInstance(MapperProxy<T> tMapperProxy) {
        return (T) Proxy
                .newProxyInstance(
                        this.mapperInterface.getClassLoader(),
                        new Class[]{mapperInterface},
                        tMapperProxy);
    }
}
