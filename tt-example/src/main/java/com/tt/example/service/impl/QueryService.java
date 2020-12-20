package com.tt.example.service.impl;


import com.tt.chxframe.annoation.Service;
import com.tt.example.service.IQueryService;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查询业务
 *
 */
@Service

public class QueryService implements IQueryService {




    /**
     * 查询
     */
    @Override
    public String query(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "{\"name\":\"" + name + "\",\"time\":\"" + time + "\"}";
        System.out.println("这是在业务方法中打印的：" + json);
        return json;
    }

}
