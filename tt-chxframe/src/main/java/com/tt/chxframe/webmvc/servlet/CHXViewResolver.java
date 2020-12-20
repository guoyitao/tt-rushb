package com.tt.chxframe.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * @author: guoyitao
 * @date: 2020/11/28 21:30
 * @version: 1.0
 */
public class CHXViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    private File templateRootDir;

    private String viewName;

    public CHXViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public CHXView resolveViewName(String viewName, Locale locale) throws Exception {
        if (null == viewName || "".equals(viewName.trim())) {
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return new CHXView(templateFile);
    }

    public String getViewName() {
        return viewName;
    }
}
