package com.kang.servlet;

import com.kang.annotation.Controller;
import com.kang.annotation.RequestMapping;
import com.kang.annotation.RequestParam;
import com.kang.domain.HanderMethod;
import com.kang.domain.HanderMethodParameter;
import com.kang.resolver.MethodParamResolver;
import com.kang.resolver.RequestParamMethodParamResolver;
import com.kang.utils.FilePathUtils;
import com.kang.utils.FileScanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Author：zeqi
 * @Date: Created in 20:23 22/5/18.
 * @Description:
 */
public class DispatcherServlet extends HttpServlet {


    //ScanPackage保存路径下的所有文件
    private Set<String> fileNames = new HashSet<String>();

    /**
     * RequestMapping的path和Method映射关系
     */
    private HashMap<String,HanderMethod> pathToMethod = new HashMap<String,HanderMethod>();

    /**
     * RequestMapping的path和Method参数映射关系
     */
    private HashMap<String,List<HanderMethodParameter>> pathToMethodParams = new HashMap<String,List<HanderMethodParameter>>();

    /**
     * 默认包路径
     */
    private String defaultPackagePath = "com.kang";
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //扫描包下所有文件
        new FileScanner().scanPackage(defaultPackagePath,fileNames);
        //实例化有效的类
        filterAndInstance();
    }

    /**
     * 实例化有效的类文件
     */
    private void filterAndInstance() {
        try {
            //不为空时
            if (fileNames.size() > 0) {
                for (String fileName: fileNames) {
                    //以.class结尾
                    if (fileName.contains(".class")) {
                        fileName = FilePathUtils.trimEndingClass(fileName);
                        Class<?> cls = Class.forName(fileName);
                        if (cls.isAnnotationPresent(Controller.class)) {
                            /*Controller conAnn = cls.getAnnotation(Controller.class);
                            conAnn.value()*/
                            Object controller= cls.newInstance();
                            //解析方法
                            Method[] methods = cls.getDeclaredMethods();
                            if (methods != null && methods.length > 0) {
                                for (Method method : methods) {
                                    String path = "";
                                    //方法上面有RequestMapping 注释
                                    if (method.isAnnotationPresent(RequestMapping.class)) {
                                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                        path = requestMapping.value();
                                        HanderMethod handerMethod = new HanderMethod(method,controller);
                                        pathToMethod.put(path,handerMethod);
                                    }
                                    //方法参数解析
                                    Parameter[] parameters = method.getParameters();
                                    if (parameters != null && parameters.length > 0) {
                                        //保存方法参数列表
                                        List<HanderMethodParameter> methodParameters = new ArrayList<>();
                                        for (Parameter parameter: parameters) {

                                            if (parameter.isAnnotationPresent(RequestParam.class)) {
                                                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                                                HanderMethodParameter methodParameter = new HanderMethodParameter(requestParam.name(),requestParam.defaultValue(),requestParam.required());
                                                methodParameters.add(methodParameter);

                                            }
                                        }
                                        pathToMethodParams.put(path,methodParameters);
                                    }
                                }
                            }

                        }

                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        resp.setContentType("text/html;charset=UTF-8");
        resp.getOutputStream().write("hello world".getBytes());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        HanderMethod handerMethod = pathToMethod.get(url);
        if (handerMethod != null) {
            //获取方法
            Method method = handerMethod.getMethod();
            //获取参数
            List<HanderMethodParameter> methodParameters = pathToMethodParams.get(url);

            //获取参数值
            Object[] paramValues = getMethodParamReslover().doResolverParams(methodParameters,req);

            //开始调用
            Object returnValue = doInvoke(handerMethod,paramValues);

            resp.getOutputStream().write(((String)returnValue).getBytes(Charset.forName("utf-8")));
        }
        resp.setContentType("text/html;charset=UTF-8");
        //resp.getOutputStream().write("hello world".getBytes());

    }

    /**
     * 开始调用
     * @param handerMethod
     * @param paramValues
     */
    private Object doInvoke(HanderMethod handerMethod, Object... paramValues) {
        try {
            return  handerMethod.getMethod().invoke(handerMethod.getBean(),paramValues);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法参数解析器
     * @return
     */
    public MethodParamResolver getMethodParamReslover() {
       return  new RequestParamMethodParamResolver();
    }
}
