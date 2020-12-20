package com.tt.sqlhelper.executor.parameter;

import com.tt.sqlhelper.mapping.BoundSql;
import com.tt.sqlhelper.mapping.MappedStatement;
import com.tt.sqlhelper.mapping.ParameterMapping;
import com.tt.sqlhelper.util.CommonUtil;
import com.tt.sqlhelper.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: guoyitao
 * @date: 2020/11/24 15:38
 * @version: 1.0
 */
public class DefaultParameterHandler implements ParameterHandler {

    private Object parameter;
    private MappedStatement mappedStatement;
    private BoundSql boundSql;

    public DefaultParameterHandler(Object parameter, MappedStatement mappedStatement, BoundSql boundSql) {
        this.parameter = parameter;
        this.mappedStatement = mappedStatement;
        this.boundSql = boundSql;
    }

    @Override
    public void setParameters(PreparedStatement ps) {

        try {
            if (parameter != null && Map.class.isAssignableFrom(parameter.getClass()) && mappedStatement
                    .getParameterType() != null) {

                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                String parameterType = mappedStatement.getParameterType();
                Class parameterTypeClass = Class.forName(parameterType);

                Map<String, Object> parameterMap = (Map<String, Object>) parameter;

                //基本类型
                if (ReflectionUtil.isJavaBaseType(parameterTypeClass)) {
                    for (ParameterMapping parameterMapping : parameterMappings) {

                        ps.setObject(parameterMapping.getNumericScale(), parameterMap
                                .get(parameterMapping.getProperty()));
                    }
                }else if (parameterType != null){

                    Object paramsObj = parameterMap.size() > 0 ? parameterMap.values().iterator().next() : null;
                    Class<?> paramsClass = paramsObj.getClass();


                    for (ParameterMapping parameterMapping : parameterMappings) {
                        Field field = paramsClass.getDeclaredField(parameterMapping.getProperty());
                        field.setAccessible(true);
                        ps.setObject(parameterMapping.getNumericScale(),field.get(paramsObj));
                    }
                }


            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }


//    @Override
//    public void setParameters(PreparedStatement ps) {
//
//        try {
//            if (parameter != null && mappedStatement.getParameterType() != null && parameter.getClass().isArray()) {
//
//                Object[] parameter1 = (Object[]) this.parameter;
//                for (Object o : parameter1) {
//                    Class<?> aClass = o.getClass();
//                    if (aClass.isAssignableFrom(Class.forName(mappedStatement.getParameterType()))) {
//                        Field[] declaredFields = aClass.getDeclaredFields();
//
////                        设置对象中的属性
//                        for (int i = 0; i < declaredFields.length; i++) {
//                            Field declaredField = declaredFields[i];
//                            declaredField.setAccessible(true);
//                            ps.setObject(i+1, declaredField.get(o));
//                        }
//
////                        ParameterType只有一个所以退出
//                        return;
//                    }
//                }
//            }
//
//
//            if (parameter != null && parameter.getClass().isArray()) {
//                Object[] params = (Object[]) parameter;
//                for (int i = 0; i < params.length; i++) {
//                    ps.setObject(i + 1, params[i]);
//                }
//
//            }
//
//        } catch (Exception throwables) {
//            throw new RuntimeException(throwables);
//        }
//    }
}
