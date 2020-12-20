package com.tt.chxframe.aop.aspect;


import com.tt.chxframe.aop.intercept.CHXMethodInterceptor;
import com.tt.chxframe.aop.intercept.CHXMethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by Tom on 2019/4/15.
 */
public class CHXAfterThrowingAdviceInterceptor extends CHXAbstractAspectAdvice implements CHXAdvice, CHXMethodInterceptor {


    private String throwingName;

    public CHXAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(CHXMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(mi,null,e.getCause());
            throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }
}
