package com.tt.chxframe.context.support;

/**
 * JoC 容器实 类的顶层抽象类 实现 roe 容器相关的公共逻辑。为了尽可能地简化，在这
 * Mini 中， 时只设计了 一个 refresh （） 方法。
 * @author: guoyitao
 * @date: 2020/11/28 16:37
 * @version: 1.0
 */
public abstract class CHXAbstractApplicationContext {
    //受保护，只提供给子类重写
    protected void refresh() throws Exception {}
}
