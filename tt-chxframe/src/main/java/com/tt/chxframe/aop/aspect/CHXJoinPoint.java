package com.tt.chxframe.aop.aspect;

import java.lang.reflect.Method;

/**
 * 回调连接点 通过它可以获得被代理的业务方法的所有信息
 * @author: guoyitao
 * @date: 2020/11/29 16:40
 * @version: 1.0
 */
public interface CHXJoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
