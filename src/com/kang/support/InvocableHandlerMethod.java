package com.kang.support;

import com.kang.convert.DataBinder;
import com.kang.domain.HandlerMethod;
import com.kang.domain.HandlerMethodParameter;
import com.kang.enums.ParamterType;
import com.kang.exception.MissArgumentException;
import com.kang.factory.BeanFactory;
import com.kang.resolver.MethodParamResolver;
import com.kang.resolver.RequestHeaderMethodParamResolver;
import com.kang.resolver.RequestParamMethodParamResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author：zeqi
 * @Date: Created in 14:24 24/5/18.
 * @Description:
 */
public class InvocableHandlerMethod {

    private BeanFactory factory;

    public InvocableHandlerMethod(BeanFactory factory) {
        this.factory = factory;
    }


    /**
     *
     * @param methodParameters
     * @param req
     * @return
     */
    public Object[] doResolverParams(List<HandlerMethodParameter> methodParameters, HttpServletRequest req) {
        //参数值数组
        Object[] paramValues = new Object[0];
        if (methodParameters != null && methodParameters.size() > 0) {
            //参数值数组
            paramValues = new Object[methodParameters.size()];
            int index = 0;
            for (HandlerMethodParameter parameter : methodParameters) {
                Object paramValue = this.getMethodParamReslover(parameter).doResolverParam(parameter,req);

                //convert Type if Necessary
                paramValues[index++] = new DataBinder().convertIfNecessary(paramValue,parameter.getParameter());
            }
        }
        return paramValues;
    }

    /**
     * 调用
     * @param req
     * @param  req
     * @return
     */
    public  Object invoke(HttpServletRequest req) {

        String url = req.getRequestURI();
        HandlerMethod handlerMethod = factory.getHanderMethod(url);
        //获取方法
        Method method = handlerMethod.getMethod();
        //获取参数
        List<HandlerMethodParameter> methodParameters = factory.getMethodParameters(url);

        //获取参数值
        Object[] paramValues = doResolverParams(methodParameters,req);

        //开始调用
        Object returnValue = doInvoke(handlerMethod,paramValues);
        return returnValue;
    }
    /**
     * 开始调用
     * @param handerMethod
     * @param paramValues
     */
    private Object doInvoke(HandlerMethod handerMethod, Object... paramValues) {
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
    public MethodParamResolver getMethodParamReslover(HandlerMethodParameter parameter) {
        if (parameter.getType() == ParamterType.PARAMTER.getType()) {
            return  new RequestParamMethodParamResolver();
        } else if (parameter.getType() == ParamterType.HEADER.getType()) {
            return new RequestHeaderMethodParamResolver();
        }
        return  null;

    }
}
