package com.tt.chxframe.beans;

/**
 * Bean rapper 主要用于封装 建后的对象实例，代理对象（ Proxy ct ）或者原生对象
 *      ( Original Object ）都由 BeanWrapper 来保存。
 * @author: guoyitao
 * @date: 2020/11/28 18:15
 * @version: 1.0
 */
public class CHXBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public CHXBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
        wrappedClass = wrappedInstance.getClass();
    }

    public Object getWrappedInstance() {
        return this.wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return this.wrappedClass;
    }
}
