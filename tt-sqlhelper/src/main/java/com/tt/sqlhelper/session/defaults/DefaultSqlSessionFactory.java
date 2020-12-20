package com.tt.sqlhelper.session.defaults;

import com.tt.sqlhelper.constants.Constant;
import com.tt.sqlhelper.session.Configuration;
import com.tt.sqlhelper.session.SqlSession;
import com.tt.sqlhelper.session.SqlSessionFactory;
import com.tt.sqlhelper.util.CommonUtil;
import com.tt.sqlhelper.util.XmlUtil;

import java.io.File;
import java.net.URL;

/**
 * @author: guoyitao
 * @date: 2020/11/23 21:43
 * @version: 1.0
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        //把.换成/
        loadMappersInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.", "/"));
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }


    /**
     * 加载配置文件
     *
     * @param
     * @return com.tt.sqlhelper.session.Configuration
     */
    @Override
    public Configuration getConfiguration() {
        return null;
    }

    private void loadMappersInfo(String dirName) {
        URL resource = DefaultSqlSessionFactory.class.getClassLoader().getResource(dirName);
        File mapperDir = new File(resource.getFile());

        if (mapperDir.isDirectory()) {
//           显示包下面的所有文件
            File[] mappers = mapperDir.listFiles();
            if (CommonUtil.isNotEmpty(mappers)) {
                for (File file : mappers) {

                    //如果是文件夹就递归
                    if (file.isDirectory()) {
                        loadMappersInfo(dirName + "/" + file.getName());
                    } else if (file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX)) {
                        XmlUtil.readMapperXml(file,this.configuration);
                    }
                }
            }
        }

    }
}
