package com.tt.sqlhelper.binding;

import com.tt.sqlhelper.annotations.Param;
import com.tt.sqlhelper.mapping.MappedStatement;
import com.tt.sqlhelper.session.SqlSession;
import com.tt.sqlhelper.util.CommonUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: guoyitao
 * @date: 2020/11/24 13:19
 * @version: 1.0
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private final SqlSession sqlSession;

    private final Class<T> mapperInterface;


    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {

        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.execute(method, args);
    }

    private Object execute(Method method, Object[] args) {
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(statementId);

        Object result = null;
        switch (ms.getSqlCommandType()) {
            case DELETE: {
                Object command = convertArgsToSqlCommandParam(method.getParameters(),args);

                result = rowCountResult(sqlSession.delete(statementId, command), method);
                break;
            }
            case INSERT: {
                Object command = convertArgsToSqlCommandParam(method.getParameters(),args);

                result = rowCountResult(sqlSession.insert(statementId, command), method);
                break;
            }
            case SELECT: {
                Object command = convertArgsToSqlCommandParam(method.getParameters(),args);

                Class<?> returnType = method.getReturnType();
                if (Collection.class.isAssignableFrom(returnType)) {
                    result = sqlSession.selectList(statementId, command);
                } else {
                    result = sqlSession.selectOne(statementId, command);
                }
                break;
            }
            case UPDATE: {
                Object command = convertArgsToSqlCommandParam(method.getParameters(),args);

                result = rowCountResult(sqlSession.update(statementId, command), method);
                break;
            }
            default:
                break;

        }

        return result;
    }

    private Object rowCountResult(int rowCount, Method method) {
        final Object result;
        if (Void.class.equals(method.getReturnType()) || Void.TYPE.equals(method.getReturnType())) {
            result = null;
        } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
            result = (long) rowCount;
        } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
            result = rowCount > 0;
        } else {
            throw new RuntimeException("Mapper method '" + "' has an unsupported return type: " + method
                    .getReturnType());
        }
        return result;
    }

    /**
     *  key:方法的参数名称
     *  value：方法的参数
     *
     * @param parameters
     * @param args
     * @return java.lang.Object
     */
    public Object convertArgsToSqlCommandParam(Parameter[] parameters, Object[] args){
        if (!CommonUtil.isNotEmpty(parameters) || !CommonUtil.isNotEmpty(args)){
            return null;
        }


        int length = parameters.length;
        Map<String,Object> params = new HashMap<>();
        for (int i = 0; i < length; i++) {
            params.put(parameters[i].getAnnotation(Param.class).value(),args[i]);
        }
        return params;
    }


}
