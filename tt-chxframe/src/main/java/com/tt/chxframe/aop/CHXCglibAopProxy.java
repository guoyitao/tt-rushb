package com.tt.chxframe.aop;


import com.tt.chxframe.aop.support.CHXAdvisedSupport;

/**
 * Created by Tom on 2019/4/14.
 */
public class CHXCglibAopProxy implements  CHXAopProxy {
    public CHXCglibAopProxy(CHXAdvisedSupport config) {
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
