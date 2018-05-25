package com.kang.factory;

import com.kang.annotation.*;
import com.kang.domain.HandlerMethod;
import com.kang.domain.HandlerMethodParameter;
import com.kang.enums.ParamterType;
import com.kang.utils.FilePathUtils;
import com.kang.utils.FileScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @Author：zeqi
 * @Date: Created in 20:26 23/5/18.
 * @Description:
 */
public class BeanFactory {


    /**
     * ScanPackage保存路径下的所有文件
     */
    private Set<String> fileNames = new HashSet<String>();

    /**
     * RequestMapping的path和Method映射关系
     */
    private HashMap<String,HandlerMethod> pathToMethod = new HashMap<String,HandlerMethod>();


    /**
     * 保存类的Type和Instance之间的映射
     */
    private HashMap<Object,Object> typeToObject = new HashMap<Object,Object>();

    /**
     * RequestMapping的path和Method参数映射关系
     */
    private HashMap<String,List<HandlerMethodParameter>> pathToMethodParams = new HashMap<String,List<HandlerMethodParameter>>();


    public BeanFactory(String defaultPackagePath) {
        //扫描包下所有文件
        new FileScanner().scanPackage(defaultPackagePath,fileNames);
    }

    /**
     * 类中的Autowired 元素进行注入
     */
    public void ioc() {

        Collection<Object> collection = typeToObject.values();
        if (collection.isEmpty()) {
            return;
        }
        //遍历集合
        for (Object object: collection) {
            Class cls = object.getClass();

            //解析field
            Field[] fields = cls.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {

                    if (field.isAnnotationPresent(AutoWired.class)) {
                        field.setAccessible(true);
                        //判断是否包含要注入的类型
                        if (typeToObject.containsKey(field.getGenericType())) {
                            try {
                                field.set(object,typeToObject.get(field.getGenericType()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }

    }


    /**
     * 实例化有效的类文件
     */
    public void filterAndInstance() {
        try {
            //不为空时
            if (fileNames.size() > 0) {
                for (String fileName: fileNames) {
                    //以.class结尾
                    if (fileName.contains(".class")) {
                        fileName = FilePathUtils.trimEndingClass(fileName);
                        Class<?> cls = Class.forName(fileName);
                        //
                        if (cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(Component.class)) {
                            //组件类型和实例之间的映射
                            typeToObject.put(cls,cls.newInstance());
                        }

                        if (cls.isAnnotationPresent(Controller.class)) {

                            Object controller= typeToObject.get(cls);
                            //解析方法
                            Method[] methods = cls.getDeclaredMethods();
                            if (methods != null && methods.length > 0) {
                                for (Method method : methods) {
                                    String path = "";
                                    //方法上面有RequestMapping 注释
                                    if (method.isAnnotationPresent(RequestMapping.class)) {
                                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                        path = requestMapping.value();
                                        HandlerMethod handerMethod = new HandlerMethod(method,controller);
                                        pathToMethod.put(path,handerMethod);
                                    }
                                    //方法参数解析
                                    Parameter[] parameters = method.getParameters();
                                    if (parameters != null && parameters.length > 0) {
                                        //保存方法参数列表
                                        List<HandlerMethodParameter> methodParameters = new ArrayList<>();
                                        for (Parameter parameter: parameters) {

                                            if (parameter.isAnnotationPresent(RequestParam.class)) {
                                                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                                                HandlerMethodParameter methodParameter = new HandlerMethodParameter(parameter,requestParam.name(),requestParam.defaultValue(),requestParam.required(), ParamterType.PARAMTER.getType());
                                                methodParameters.add(methodParameter);

                                            } else if (parameter.isAnnotationPresent(RequestHeader.class)) {
                                                RequestHeader requestParam = parameter.getAnnotation(RequestHeader.class);
                                                HandlerMethodParameter methodParameter = new HandlerMethodParameter(parameter,requestParam.name(),requestParam.defaultValue(),requestParam.required(),ParamterType.HEADER.getType());
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


    /**
     *
     * @param url
     * @return
     */
    public HandlerMethod getHanderMethod(String url) {
        return  pathToMethod.get(url);
    }


    /**
     * 获取参数
     * @param url
     * @return
     */
    public List<HandlerMethodParameter> getMethodParameters(String url) {
        return pathToMethodParams.get(url);
    }
}
