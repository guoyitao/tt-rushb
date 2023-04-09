package com.tt.example.action;

import com.tt.chxframe.annoation.Controller;
import com.tt.chxframe.annoation.RequestMapping;
import com.tt.chxframe.webmvc.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/list")
public class ListController {

    @RequestMapping("/list.json")
    public ModelAndView modelAndViewList(){
        ModelAndView modelAndView = new ModelAndView("list");
        Map<String,Object> stringObjectMap = new HashMap<>();
        modelAndView.setModel(stringObjectMap);
        return modelAndView;
    }

    private ModelAndView out(HttpServletResponse resp, String str){
        try {
            resp.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
