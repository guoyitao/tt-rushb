package com.tt.sqlhelper.mapping;

/**
 * @author: guoyitao
 * @date: 2020/11/24 19:37
 * @version: 1.0
 */
public class ParameterMapping {
//    属性名
    private String property;
//    sql中?的位置
    private Integer numericScale;

    public ParameterMapping(String property, Integer numericScale) {
        this.property = property;
        this.numericScale = numericScale;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Integer getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(Integer numericScale) {
        this.numericScale = numericScale;
    }

    @Override
    public String toString() {
        return "ParameterMapping{" + "property='" + property + '\'' + ", numericScale=" + numericScale + '}';
    }
}
