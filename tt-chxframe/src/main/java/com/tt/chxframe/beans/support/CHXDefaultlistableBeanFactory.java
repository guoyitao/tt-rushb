package com.tt.chxframe.beans.support;

import com.tt.chxframe.beans.config.CHXBeanDefinition;
import com.tt.chxframe.context.support.CHXAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: guoyitao
 * @date: 2020/11/28 18:18
 * @version: 1.0
 */
public class CHXDefaultlistableBeanFactory extends CHXAbstractApplicationContext {
    //存储注册信息的 BeanDefinition
    protected final Map<String, CHXBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

}
