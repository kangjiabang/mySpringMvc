package com.kang.resolver;

import com.kang.domain.HandlerMethodParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author：zeqi
 * @Date: Created in 15:37 23/5/18.
 * @Description:
 */
public interface MethodParamResolver {

     Object doResolverParam(HandlerMethodParameter methodParameter, HttpServletRequest req);

}
