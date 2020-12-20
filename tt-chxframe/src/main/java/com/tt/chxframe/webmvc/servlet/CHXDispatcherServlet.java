package com.tt.chxframe.webmvc.servlet;


import com.tt.chxframe.annoation.Controller;
import com.tt.chxframe.annoation.RequestMapping;
import com.tt.chxframe.context.CHXApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: guoyitao
 * @date: 2020/11/28 16:50
 * @version: 1.0
 */
public class CHXDispatcherServlet extends HttpServlet {
    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
    private final String TEMPLATE_ROOT = "templateRoot";

    private CHXApplicationContext context;

    private List<CHXHandlerMapping> handlerMappings = new ArrayList<>();

    private Map<CHXHandlerMapping, CHXHandlerAdapter> handlerAdapters = new HashMap<>();

    private List<CHXViewResolver> viewResolvers = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter()
                .write("500 Exception,Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "")
                                                            .replaceAll(",\\s", "\r\n"));
            e.printStackTrace();


        }
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、初始化ApplicationContext
        context = new CHXApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2、初始化MVC 九大组件
        initStrategies(context);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        CHXHandlerMapping handler = getHandler(req);

        if (handler == null) {
            processDispatchResult(req, resp, new ModelAndView("404"));
            return;
        }

        //2、准备调用前的参数
        CHXHandlerAdapter ha = getHandlerAdapter(handler);

        //3、真正的调用方法,返回ModelAndView存储了要穿页面上值，和页面模板的名称
        ModelAndView mv = ha.handle(req, resp, handler);

        //这一步才是真正的输出
        processDispatchResult(req, resp, mv);
    }

    private CHXHandlerAdapter getHandlerAdapter(CHXHandlerMapping handler) {
        if (this.handlerAdapters.isEmpty()) {
            return null;
        }
        CHXHandlerAdapter ha = this.handlerAdapters.get(handler);
        if (ha.supports(handler)) {
            return ha;
        }
        return null;

    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ModelAndView mv) throws Exception {
        //把给我的ModleAndView变成一个HTML、OuputStream、json、freemark、veolcity
        //ContextType
        if (null == mv) {
            return;
        }

        //如果ModelAndView不为null，怎么办？
        if (this.viewResolvers.isEmpty()) {
            return;
        }

        for (CHXViewResolver viewResolver : this.viewResolvers) {
            CHXView view = viewResolver.resolveViewName(mv.getViewName(), null);
            if (view != null) {
                view.render(mv.getModel(), req, resp);
                return;
            }
        }
    }

    private CHXHandlerMapping getHandler(HttpServletRequest req) {
        if (this.handlerMappings.isEmpty()) {
            return null;
        }

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");

        for (CHXHandlerMapping handler : this.handlerMappings) {

            Matcher matcher = handler.getPattern().matcher(url);
            //如果没有匹配上继续下一个匹配
            if (!matcher.matches()) {
                continue;
            }

            return handler;
        }
        return null;
    }

    private void initStrategies(CHXApplicationContext context) {
        //有九 策略
        //针对每个用户请求，都会经过一些处理策略处理，最终才能有结果输出
        //每种策略可以自定义干预，但是最终的结果都一致

        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);


        //保存 Controlle 中配置的 RequestMapping Method 的对应关系
        initHandlerMappings(context);
        //Handle Adapter5 用来动态匹配 Method 参数，包括类转换、动态赋值
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        // 初始化视图预处理器
        initRequestToViewNameTranslator(context);


        //初始化视图转换器，必须实现
        initViewResolvers(context);
        //参数缓存器
        initFlashMapManager(context);
    }
    private void initMultipartResolver(CHXApplicationContext context) {
    }
    private void initLocaleResolver(CHXApplicationContext context) {
    }
    private void initThemeResolver(CHXApplicationContext context) {
    }
    private void initHandlerMappings(CHXApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();

        try {
            for (String beanName : beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();

                if (!clazz.isAnnotationPresent(Controller.class)) {
                    continue;
                }
                String baseUrl = "";
                //获取Controller的url配置

                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = requestMapping.value();
                }

                //扫描所有的 public 类型的方法
                //获取Method的url配置
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {

                    //没有加RequestMapping注解的直接忽略
                    if (!method.isAnnotationPresent(RequestMapping.class)) {
                        continue;
                    }

                    //映射URL
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    //  /demo/query

                    //  (//demo//query)

                    String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*", ".*"))
                            .replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);

                    this.handlerMappings.add(new CHXHandlerMapping(pattern, controller, method));

                    System.out.println("Mapped " + regex + "," + method);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHandlerAdapters(CHXApplicationContext context) {

        //把一个requet请求变成一个handler，参数都是字符串的，自动配到handler中的形参
        //可想而知，他要拿到HandlerMapping才能干活
        //就意味着，有几个HandlerMapping就有几个HandlerAdapter

        //在初始 阶段 能做的就是，将这些参数的名字或者类型按 定的顺序保存
        //因为后面用反射调用的时候，传的形参是一个数组
        //可以通过记录这些参数 位置 index ，逐个从数组中取值，这样就和参数的顺序无关了
        for (CHXHandlerMapping handlerMapping : this.handlerMappings) {
            //每个方法有一个参数列表，这里保存的是形参列表
            this.handlerAdapters.put(handlerMapping, new CHXHandlerAdapter());
        }
    }

    private void initFlashMapManager(CHXApplicationContext context) {


    }

    private void initViewResolvers(CHXApplicationContext context) {
        //拿到模板的存放目录
        String templateRoot = context.getConfig().getProperty(TEMPLATE_ROOT);
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);

        String[] templates = templateRootDir.list();

        for (int i = 0; i < templates.length; i++) {
            //这里主要是为了兼容多模板，所有模仿Spring用List保存
            //在我写的代码中简化了，其实只有需要一个模板就可以搞定
            //只是为了仿真，所有还是搞了个List
            this.viewResolvers.add(new CHXViewResolver(templateRoot));
        }

    }

    private void initRequestToViewNameTranslator(CHXApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(CHXApplicationContext context) {

    }


}
