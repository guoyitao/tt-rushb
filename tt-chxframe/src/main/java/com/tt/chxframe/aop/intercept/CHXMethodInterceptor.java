package com.tt.chxframe.aop.intercept;

/**
 * 方法拦截器是 AOP 代码增强的基本组成单元
 * @author: guoyitao
 * @date: 2020/11/29 16:42
 * @version: 1.0
 */
public interface CHXMethodInterceptor {
    Object invoke(CHXMethodInvocation invocation) throws Throwable;
}
