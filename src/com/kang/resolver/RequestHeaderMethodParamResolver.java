package com.kang.resolver;

import com.kang.domain.HandlerMethodParameter;
import com.kang.exception.MissArgumentException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zeqi
 * @Date: Created in 15:37 23/5/18.
 * @Description:
 */
public class RequestHeaderMethodParamResolver extends CommonMethodParamResolver {


    @Override
    public Object getParamValue(HttpServletRequest req,String name) {
        return req.getHeader(name);
    }


}
