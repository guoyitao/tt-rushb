package com.tt.chxframe.webmvc.servlet;

import java.util.Map;

/**
 * @author: guoyitao
 * @date: 2020/11/28 22:20
 * @version: 1.0
 */
public class ModelAndView {

    private String viewName;
    private Map<String,?> model;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
