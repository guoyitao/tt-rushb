package com.tt.sqlhelper.util;

/**
 * @author: guoyitao
 * @date: 2020/11/24 21:06
 * @version: 1.0
 */
public class ReflectionUtil {
    public static boolean isJavaBaseType(Class clazz ){
        if (Integer.class.equals(clazz)){
            return true;
        }else if (String.class.equals(clazz)){
            return true;
        }else if (Character.class.equals(clazz)) {
            return true;
        }else if (Double.class.equals(clazz)) {
            return true;
        }else if (Short.class.equals(clazz)) {
            return true;
        }else if (Long.class.equals(clazz)) {
            return true;
        }
        return false;
    }
}
