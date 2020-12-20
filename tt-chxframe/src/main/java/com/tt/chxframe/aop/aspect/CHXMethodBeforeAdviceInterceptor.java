package com.tt.chxframe.aop.aspect;


import com.tt.chxframe.aop.intercept.CHXMethodInterceptor;
import com.tt.chxframe.aop.intercept.CHXMethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by Tom on 2019/4/15.
 */
public class CHXMethodBeforeAdviceInterceptor extends CHXAbstractAspectAdvice implements CHXAdvice, CHXMethodInterceptor {


    private CHXJoinPoint joinPoint;
    public CHXMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method,Object[] args,Object target) throws Throwable{
        //传送了给织入参数
        //method.invoke(target);
        super.invokeAdviceMethod(this.joinPoint,null,null);

    }
    @Override
    public Object invoke(CHXMethodInvocation mi) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinPoint = mi;
        before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
