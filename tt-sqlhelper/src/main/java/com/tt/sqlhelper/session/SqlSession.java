package com.tt.sqlhelper.session;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author: guoyitao
 * @date: 2020/11/23 20:06
 * @version: 1.0
 */
public interface SqlSession {

    /**
     * 查询带条记录
     *
     * @param statementId sql的唯一标识
     * @param parameter 参数
     * @return Mapped object
     */
    <T> T selectOne(String statementId, Object parameter);

    /**
     * 查询多条记录
     *
     * @param statementId sql的唯一标识
     * @param parameter  参数
     * @return Mapped object
     */
    <E> List<E> selectList(String statementId, Object parameter) ;

    /**
     *  更新
     * @param statementId sql的唯一标识
     * @param parameter 参数
     * @return 受更新影响的行数
     */
    int  update(String statementId, Object parameter);

    /**
     * 插入
     * @param statementId sql的唯一标识
     * @param parameter 参数
     * @return 受插入影响的行数
     */
    int  insert(String statementId, Object parameter);

    /**
     * 删除
     * @param statementId sql的唯一标识
     * @param parameter 参数
     * @return 受删除影响的行数
     */
    int  delete(String statementId, Object parameter);

    /**
     * 获取mapper
     *
     * @param paramClass mapper的接口类型
     * @return  a mapper bound to this SqlSession
     */
    <T> T getMapper(Class<T> paramClass);

    /**
     * 获取配置类
     *
     * @return 配置类
     */
    Configuration getConfiguration();
}
