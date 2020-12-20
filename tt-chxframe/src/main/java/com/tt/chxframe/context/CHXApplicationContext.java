package com.tt.chxframe.context;

import com.tt.chxframe.annoation.Autowired;
import com.tt.chxframe.annoation.Controller;
import com.tt.chxframe.annoation.Dao;
import com.tt.chxframe.annoation.Service;
import com.tt.chxframe.aop.CHXAopProxy;
import com.tt.chxframe.aop.CHXCglibAopProxy;
import com.tt.chxframe.aop.CHXJdkDynamicAopProxy;
import com.tt.chxframe.aop.config.CHXAopConfig;
import com.tt.chxframe.aop.support.CHXAdvisedSupport;
import com.tt.chxframe.beans.CHXBeanWrapper;
import com.tt.chxframe.beans.config.CHXBeanDefinition;
import com.tt.chxframe.beans.config.CHXBeanPostProcessor;
import com.tt.chxframe.beans.support.CHXBeanDefinitionReader;
import com.tt.chxframe.beans.support.CHXDefaultlistableBeanFactory;
import com.tt.chxframe.core.CHXBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: guoyitao
 * @date: 2020/11/28 16:37
 * @version: 1.0
 */
public class CHXApplicationContext extends CHXDefaultlistableBeanFactory implements CHXBeanFactory {

    private String[] configLoactions;
    private CHXBeanDefinitionReader reader;

    //单例的IOC容器缓存
    private Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    //通用的IOC容器
    private Map<String, CHXBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();


    public CHXApplicationContext(String... configLoactions) {
        this.configLoactions = configLoactions;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void refresh() throws Exception {
        //1、定位，定位配置文件
        reader = new CHXBeanDefinitionReader(this.configLoactions);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<CHXBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);

        //4、把不是延时加载的类，有提前初始化
        doAutowrited();
    }
    //只处理非延时加载的情况
    private void doAutowrited() {
        for (Map.Entry<String, CHXBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        注入
        for (Map.Entry<String, CHXBeanWrapper> entry : factoryBeanInstanceCache.entrySet()) {
            CHXBeanWrapper beanWrapper = entry.getValue();
            populateBean(beanWrapper.getWrappedInstance().getClass().getName(),new CHXBeanDefinition(),beanWrapper);
        }
    }

    private void doRegisterBeanDefinition(List<CHXBeanDefinition> beanDefinitions) throws Exception {

        for (CHXBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //到这里为止，容器初始化完毕
    }


    /**
     *   依赖注入，从这里开始，通过读取BeanDefinition中的信息
     *   然后，通过反射机制创建一个实例并返回
     *   Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
     *   装饰器模式：
     *   1、保留原来的OOP关系
     *   2、我需要对它进行扩展，增强（为了以后AOP打基础）
     * @param beanName
     * @return java.lang.Object
     */
    @Override
    public Object getBean(String beanName) throws Exception {
        CHXBeanDefinition chxBeanDefinition = super.beanDefinitionMap.get(beanName);
        Object instance = null;
        try {
            //这个逻辑还不严谨，自己可以去参考Spring源码
            //工厂模式 + 策略模式
            CHXBeanPostProcessor postProcessor = new CHXBeanPostProcessor();

            instance = instantiateBean(chxBeanDefinition);

            postProcessor.postProcessAfterInitialization(instance,beanName);
            //3、把这个对象封装到BeanWrapper中
            CHXBeanWrapper chxBeanWrapper = new CHXBeanWrapper(instance);

            //4、把BeanWrapper存到IOC容器里面
            this.factoryBeanInstanceCache.put(beanName,chxBeanWrapper);

            postProcessor.postProcessBeforeInitialization(instance,beanName);

//            TODO err 注入 bean都没创建注入你吗的b的注入？  在这里注入的时候其他类都没创建，根本没方法注入
//            populateBean(beanName,chxBeanDefinition,chxBeanWrapper);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, CHXBeanDefinition gpBeanDefinition, CHXBeanWrapper gpBeanWrapper) {
        Object instance = gpBeanWrapper.getWrappedInstance();

        Class<?> clazz = gpBeanWrapper.getWrappedClass();
        //判断只有加了注解的类，才执行依赖注入
        if(!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class) ||(clazz.isAnnotationPresent(Dao.class)))){
            return;
        }

        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAnnotationPresent(Autowired.class)){
                continue;
            }

            Autowired autowired = field.getAnnotation(Autowired.class);

            String autowiredBeanName =  autowired.value().trim();

            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }

            //强制访问
            field.setAccessible(true);

            try {
                //为什么会为NULL，先留个坑
                if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null){
                    autowiredBeanName = field.getName();
                    if (this.factoryBeanInstanceCache.get(autowiredBeanName) == null){
                        continue;
                    }
                }

                field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }

    /**
     * BeanDefinition 就返回一个实例 Bean
     * @param chxBeanDefinition
     * @return java.lang.Object
     */
    private Object instantiateBean(CHXBeanDefinition chxBeanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = chxBeanDefinition.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {

            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if(this.factoryBeanObjectCache.containsKey(className)){
                instance = this.factoryBeanObjectCache.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();

                
                CHXAdvisedSupport config = instantionAopConfig(chxBeanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);

                //符合PointCut的规则的话，创建代理对象
                if(config.pointCutMatch()) {
                    instance = createProxy(config).getProxy();
                }

                this.factoryBeanObjectCache.put(className,instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return instance;
    }

    private CHXAopProxy createProxy(CHXAdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length > 0){
            return new CHXJdkDynamicAopProxy(config);
        }
        return new CHXCglibAopProxy(config);
    }

    private CHXAdvisedSupport instantionAopConfig(CHXBeanDefinition chxBeanDefinition) {
        CHXAopConfig config = new CHXAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new CHXAdvisedSupport(config);
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new  String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
