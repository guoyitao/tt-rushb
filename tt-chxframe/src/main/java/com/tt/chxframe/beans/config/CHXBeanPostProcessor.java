package com.tt.chxframe.beans.config;

/**
 * 原生 Spring 中的 BeanPostProcess or 是为对象初始化事件设置的一利 回调机制。这个 Mini
 * 本中只做说明，不做具体实现
 * @author: guoyitao
 * @date: 2020/11/28 19:35
 * @version: 1.0
 */
public class CHXBeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

}
