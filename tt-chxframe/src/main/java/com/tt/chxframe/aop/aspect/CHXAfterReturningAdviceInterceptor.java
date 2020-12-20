package com.tt.chxframe.aop.aspect;


import com.tt.chxframe.aop.intercept.CHXMethodInterceptor;
import com.tt.chxframe.aop.intercept.CHXMethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by Tom on 2019/4/15.
 */
public class CHXAfterReturningAdviceInterceptor extends CHXAbstractAspectAdvice implements CHXAdvice, CHXMethodInterceptor {

    private CHXJoinPoint joinPoint;

    public CHXAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(CHXMethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,retVal,null);
    }
}
