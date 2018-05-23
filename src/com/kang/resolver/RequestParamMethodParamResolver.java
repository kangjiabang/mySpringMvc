package com.kang.resolver;

import com.kang.domain.HanderMethodParameter;
import com.kang.exception.MissArgumentException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author：zeqi
 * @Date: Created in 15:37 23/5/18.
 * @Description:
 */
public class RequestParamMethodParamResolver implements  MethodParamResolver {


    @Override
    public Object[] doResolverParams(List<HanderMethodParameter> methodParameters,HttpServletRequest req) {
        //参数值数组
        Object[] paramValues = new Object[0];
        if (methodParameters != null && methodParameters.size() > 0) {
            //参数值数组
            paramValues = new Object[methodParameters.size()];
            int index = 0;
            for (HanderMethodParameter parameter : methodParameters) {
                String name = parameter.getName();
                //参数值
                String paramValue = req.getParameter(name);
                //如果请求值为空，取默认值
                if (paramValue == null || paramValue == "") {
                    //如果必选
                    if (parameter.isRequired()) {
                        throw new MissArgumentException("parameter " + name + " can not be Null.");
                    }
                    paramValue = parameter.getDefaultValue();
                }
                paramValues[index++] = paramValue;
            }
        }
        return paramValues;
    }

}
