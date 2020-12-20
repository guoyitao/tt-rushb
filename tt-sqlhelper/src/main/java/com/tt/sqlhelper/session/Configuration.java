package com.tt.sqlhelper.session;

import com.tt.sqlhelper.binding.MapperRegistry;
import com.tt.sqlhelper.mapping.MappedStatement;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置类
 *
 * @author: guoyitao
 * @date: 2020/11/23 21:22
 * @version: 1.0
 */
public class Configuration {
    /**
     * 配置项
     */
    public static Properties PROPS = new Properties();

    /**
     * mapper代理注册器
     */
    protected final MapperRegistry mapperRegistry = new MapperRegistry();

    /**
     * mapper文件的select/update语句的id和SQL语句属性
     **/
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();


    public <T> void addMapper(Class<T> type) {
        this.mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    /**
     *
     * @param key sqlid
     * @param mappedStatement sql描述详情
     * @return void
     */
    public void addMappedStatement(String key, MappedStatement mappedStatement) {
        this.mappedStatements.put(key, mappedStatement);
    }


    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }

    /**
     *  获得配置属性，不存在为""
     * @param key
     * @return java.lang.String
     *
     */
    public static String getProperty(String key) {
        return getProperty(key, "");
    }

    /**
     *  获得配置属性
     * @param key
     * @return java.lang.String
     *
     */
    public static String getProperty(String key, String defaultValue) {

        return PROPS.containsKey(key) ? PROPS.getProperty(key) : defaultValue;
    }


}
