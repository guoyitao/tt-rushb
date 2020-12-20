package com.tt.example.action;

import com.tt.chxframe.annoation.Autowired;
import com.tt.chxframe.annoation.Controller;
import com.tt.chxframe.annoation.RequestMapping;
import com.tt.chxframe.annoation.RequestParam;
import com.tt.chxframe.webmvc.servlet.ModelAndView;
import com.tt.example.service.IQueryService;
import com.tt.example.service.impl.QueryService;

import java.util.HashMap;

/**
 * @author: guoyitao
 * @date: 2020/11/29 15:26
 * @version: 1.0
 */
@Controller
public class PageAction {
    @Autowired
    IQueryService queryService;

    @RequestMapping(value = "/first.html")
    public ModelAndView query(@RequestParam("teacher") String teacher){
        String query = queryService.query(teacher);
        HashMap<String, Object> model = new HashMap<>();
        model.put("teacher",teacher);
        model.put("data",query);
        model.put("token","123456");

        return new ModelAndView("first.html",model);

    }

}
