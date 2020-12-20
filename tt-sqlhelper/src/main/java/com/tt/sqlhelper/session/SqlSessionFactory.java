package com.tt.sqlhelper.session;

/**
 * Creates an {@link SqlSession} out of a connection or a DataSource
 * @author: guoyitao
 * @date: 2020/11/23 20:05
 * @version: 1.0
 */
public interface SqlSessionFactory {

    /**
     * 开启数据库会话
     */
    SqlSession openSession();

    /**
     * 获取配置类
     */
    Configuration getConfiguration();
}
