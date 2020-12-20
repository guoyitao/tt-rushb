package com.tt.chxframe.context;

/**
 * 通过解耦方式获得IOC容器的顶层设计
 * 后面将通过一个监听器去扫描所有的类，只要实现了此接口，
 * 将自动调用setApplicationContext()方法，从而将IOC容器注入到目标类中
 * @author: guoyitao
 * @date: 2020/11/28 19:24
 * @version: 1.0
 */
public interface CHXApplicationContextAware {
    void setApplicationContext(CHXApplicationContext applicationContext);
}
