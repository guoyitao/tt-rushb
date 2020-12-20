package com.tt.chxframe.core;

/**
 * 单例工厂的顶层设计
 * @author: guoyitao
 * @date: 2020/11/28 16:40
 * @version: 1.0
 */
public interface CHXBeanFactory {
    /**
     * 根据beanName从IOC容器中获得一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

    Object getBean(Class<?> beanClass) throws Exception;
}
