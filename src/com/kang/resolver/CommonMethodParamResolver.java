package com.kang.resolver;

import com.kang.domain.HandlerMethodParameter;
import com.kang.exception.MissArgumentException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zeqi
 * @Date: Created in 15:37 23/5/18.
 * @Description:
 */
public abstract class CommonMethodParamResolver implements MethodParamResolver {


    /**
     * 获取对应参数值
     * @param parameter
     * @param req
     * @return
     */
    @Override
    public Object doResolverParam(HandlerMethodParameter parameter, HttpServletRequest req) {
        //参数值数组
        String name = parameter.getName();
        //参数值
        Object paramValue = getParamValue(req,name);
        //如果请求值为空，取默认值
        if (paramValue == null || paramValue == "") {
            //如果必选
            if (parameter.isRequired()) {
                throw new MissArgumentException("parameter " + name + " can not be Null.");
            }
            paramValue = parameter.getDefaultValue();
        }

        return paramValue;
    }

    /**
     * 获取参数值
     * @param req
     * @return
     */
    public abstract Object getParamValue(HttpServletRequest req,String name);

}
