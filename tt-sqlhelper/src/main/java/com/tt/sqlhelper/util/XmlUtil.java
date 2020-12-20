package com.tt.sqlhelper.util;

import com.tt.sqlhelper.constants.Constant;
import com.tt.sqlhelper.constants.Constant.SqlType;
import com.tt.sqlhelper.mapping.MappedStatement;
import com.tt.sqlhelper.mapping.MySqlSourse;
import com.tt.sqlhelper.mapping.ParameterMapping;
import com.tt.sqlhelper.mapping.SqlSource;
import com.tt.sqlhelper.session.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: guoyitao
 * @date: 2020/11/23 22:00
 * @version: 1.0
 */
public class XmlUtil {


    public static void readMapperXml(File file, Configuration configuration) {

        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(Constant.CHARSET_UTF8);
            Document read = saxReader.read(file);

            //根元素
            Element rootElement = read.getRootElement();

            if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName())) {
                System.err.printf("%s 的根元素不是mapper", file.getName());
                return;
            }

            String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);


            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String eleName = element.getName();

                MappedStatement statement = new MappedStatement();

                if (SqlType.UPDATE.value().equals(eleName)) {
                    statement.setSqlCommandType(SqlType.UPDATE);
                    statement.setResultType(Constant.RESULT_TYPE_AFFECT_ROW);

                } else if (SqlType.SELECT.value().equals(eleName)) {
                    String resultType = element.attributeValue(Constant.XML_SELECT_RESULTTYPE);
                    statement.setResultType(resultType);
                    statement.setSqlCommandType(SqlType.SELECT);

                } else if (SqlType.INSERT.value().equals(eleName)) {
                    statement.setSqlCommandType(SqlType.INSERT);
                    statement.setResultType(Constant.RESULT_TYPE_AFFECT_ROW);

                } else if (SqlType.DELETE.value().equals(eleName)) {
                    statement.setSqlCommandType(SqlType.DELETE);
                    statement.setResultType(Constant.RESULT_TYPE_AFFECT_ROW);

                } else {
                    // 其他标签自己实现
                    System.err.println("不支持此xml标签解析:" + eleName);
                    statement.setSqlCommandType(SqlType.DEFAULT);
                }

                String sqlId = namespace + "." + element.attributeValue(Constant.XML_ELEMENT_ID);

                statement.setParameterType(element.attributeValue(Constant.XML_PARAMETERTYPE));

                statement.setNamespace(namespace);
                statement.setSqlId(sqlId);
                String sql = CommonUtil.stringTrim(element.getStringValue());
                statement.setSql(sql);
                statement.setSqlSource(parseSqlSourse(sql,configuration ));

                System.out.println(statement);
                configuration.addMappedStatement(sqlId, statement);

            }
            configuration.addMapper(Class.forName(namespace));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static SqlSource parseSqlSourse(String sql, Configuration configuration) {
        Matcher matcher = RegexUtil.param_pattern.matcher(sql);
        SqlSource sqlSource;
        List<ParameterMapping> parameterMappings = new ArrayList<>();

        int numericScale = 1;
        while (matcher.find()) {

            String property = matcher.group(1);
            ParameterMapping mapping = new ParameterMapping(property, numericScale);
            parameterMappings.add(mapping);
            numericScale++;
        }

        if (parameterMappings.size() > 0 ){
            sqlSource = new MySqlSourse(sql,parameterMappings,configuration);
        }else{
            sqlSource = new MySqlSourse(configuration,sql);
        }


        return sqlSource;
    }



    @Test
    public void test1() {
        SqlSource sqlSource = parseSqlSourse("update user set name = #{name} where id = #{id}", null);


    }


}
