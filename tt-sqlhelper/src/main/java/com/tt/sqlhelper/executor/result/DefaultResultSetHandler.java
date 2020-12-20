package com.tt.sqlhelper.executor.result;

import com.tt.sqlhelper.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: guoyitao
 * @date: 2020/11/24 15:47
 * @version: 1.0
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private MappedStatement ms;


    public DefaultResultSetHandler(MappedStatement ms) {
        this.ms = ms;
    }

    /*
     * 处理查询结果，通过反射设置到返回的实体类
     * @param resultSet
     * @return java.util.List<E>
     */
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet) {

        List<E> result = new ArrayList<>();
        if (resultSet == null) {
            return null;
        }
        try {
            while (resultSet.next()) {
                Class<?> resultType = Class.forName(ms.getResultType());
                E enity = (E) resultType.newInstance();
                Field[] fields = resultType.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    if (String.class.equals(fieldType)) {
                        field.set(enity, resultSet.getString(field.getName()));
                    } else if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
                        field.set(enity, resultSet.getInt(field.getName()));
                    } else {
                        field.set(enity, resultSet.getObject(field.getName()));
                    }
                }
                result.add(enity);
            }

            return result;
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
