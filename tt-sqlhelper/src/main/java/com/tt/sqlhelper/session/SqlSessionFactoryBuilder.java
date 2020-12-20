package com.tt.sqlhelper.session;

import com.tt.sqlhelper.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: guoyitao
 * @date: 2020/11/23 22:32
 * @version: 1.0
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(String fileName){
        InputStream resourceAsStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileName);
        return build(resourceAsStream);
    }

    public SqlSessionFactory build(InputStream inputStream){
        try {
            Configuration.PROPS.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(new Configuration());
    }
}
