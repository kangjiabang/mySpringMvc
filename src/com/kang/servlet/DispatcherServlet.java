package com.kang.servlet;

import com.kang.factory.BeanFactory;
import com.kang.support.InvocableHandlerMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author：zeqi
 * @Date: Created in 20:23 22/5/18.
 * @Description:
 */
public class DispatcherServlet extends HttpServlet {


    /**
     * 默认包路径
     */
    private String defaultPackagePath = "com.kang";

    private BeanFactory factory;
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

        factory = new BeanFactory(defaultPackagePath);

        //实例化有效的类
        factory.filterAndInstance();

        //依赖注入AutoWired部分
        factory.ioc();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        resp.setContentType("text/html;charset=UTF-8");
        resp.getOutputStream().write("hello world".getBytes());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        InvocableHandlerMethod invocableHandlerMethod = createInvocableHandlerMethod(factory);

        //开始调用
        Object returnValue = invocableHandlerMethod.invoke(req);

        resp.getOutputStream().write(((String)returnValue).getBytes(Charset.forName("utf-8")));

        //resp.getOutputStream().write("hello world".getBytes());

    }

    /**
     *
     * @param factory
     * @return
     */
    private InvocableHandlerMethod createInvocableHandlerMethod(BeanFactory factory) {
        return new InvocableHandlerMethod(factory);
    }




}
