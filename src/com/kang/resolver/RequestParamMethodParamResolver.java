package com.kang.resolver;

import com.kang.domain.HandlerMethodParameter;
import com.kang.exception.MissArgumentException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author：zeqi
 * @Date: Created in 15:37 23/5/18.
 * @Description:
 */
public class RequestParamMethodParamResolver extends CommonMethodParamResolver {

    @Override
    public Object getParamValue(HttpServletRequest req, String name) {
        String[] strings =  req.getParameterValues(name);
        if (strings != null) {
            return strings.length == 1 ? strings[0] : strings;
        }
        return null;
    }
}
