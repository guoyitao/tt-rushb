package com.tt.chxframe.beans.config;

/**
 * BeanDefinition 主要用于保存 Bean 关的配置信息。
 * @author: guoyitao
 * @date: 2020/11/28 18:12
 * @version: 1.0
 */
public class CHXBeanDefinition {
    private String beanClassName;//原生 Bean 的全类名
    private boolean lazyInit = false; //标记是否延时加载
    private String factoryBeanName;//保存 bean Name ，在 IoC 容器中存储的 key
    private boolean isSingleton = true;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    @Override
    public String toString() {
        return "CHXBeanDefinition{" + "beanClassName='" + beanClassName + '\'' + ", lazyInit=" + lazyInit + ", factoryBeanName='" + factoryBeanName + '\'' + ", isSingleton=" + isSingleton + '}';
    }
}
