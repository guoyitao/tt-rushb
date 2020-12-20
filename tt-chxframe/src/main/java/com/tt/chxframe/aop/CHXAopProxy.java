package com.tt.chxframe.aop;

/**
 * 代理工厂的顶层接口 提供获取代理对象的顶层入口
 * 默认就用 JDK 动态代理
 * @author: guoyitao
 * @date: 2020/11/29 16:56
 * @version: 1.0
 */
public interface CHXAopProxy {
    Object getProxy();


    Object getProxy(ClassLoader classLoader);
}
