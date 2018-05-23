package com.kang.utils;

/**
 * @Author：zeqi
 * @Date: Created in 14:24 23/5/18.
 * @Description:
 */
public class FilePathUtils {

    /**
     * 将结尾的.class 文件去除
     * @param fileName
     * @return
     */
    public static String trimEndingClass(String fileName) {
        if (fileName == "" || fileName == null) {
            return null;
        }
        return fileName.substring(0,fileName.indexOf(".class"));
    }
}
