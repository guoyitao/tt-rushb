package com.tt.chxframe.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: guoyitao
 * @date: 2020/11/28 22:29
 * @version: 1.0
 */
public class CHXView {
    public final String DEFULAT_CONTENT_TYPE = "text/html;charset=utf-8";
    private static final Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}",Pattern.CASE_INSENSITIVE);
    private File viewFile;

    public CHXView(File viewFile) {
        this.viewFile = viewFile;
    }

    public void render(Map<String, ?> model,
                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        StringBuffer sb = new StringBuffer();

        RandomAccessFile ra = new RandomAccessFile(this.viewFile,"r");

        String line  = null;
        while (null != (line = ra.readLine())){
            line = new String(line.getBytes("ISO-8859-1"),"utf-8");

            Matcher matcher = pattern.matcher(line);
            while (matcher.find()){
                String paramName = matcher.group();
                paramName = paramName.replaceAll("￥\\{|\\}","");
                Object paramValue = model.get(paramName);
                if(null == paramValue){ continue;}
                line = matcher.replaceFirst(makeStringForRegExp(paramValue.toString()));
                matcher = pattern.matcher(line);
            }
            sb.append(line);
        }

        response.setCharacterEncoding("utf-8");
        response.setContentType(DEFULAT_CONTENT_TYPE);
        response.getWriter().write(sb.toString());
    }


    //处理特殊字符
    public static String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                  .replace("+", "\\+").replace("|", "\\|")
                  .replace("{", "\\{").replace("}", "\\}")
                  .replace("(", "\\(").replace(")", "\\)")
                  .replace("^", "\\^").replace("$", "\\$")
                  .replace("[", "\\[").replace("]", "\\]")
                  .replace("?", "\\?").replace(",", "\\,")
                  .replace(".", "\\.").replace("&", "\\&");
    }
}
