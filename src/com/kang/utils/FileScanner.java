package com.kang.utils;

import java.io.File;
import java.net.URL;
import java.util.Set;

/**
 * @Author：zeqi
 * @Date: Created in 14:06 23/5/18.
 * @Description:
 */
public class FileScanner {



    /**
     * 扫描 包下所有的文件
     * @param path
     */
    public void scanPackage(String path,Set<String> fileNames) {
        try {
            URL url = this.getClass().getClassLoader().getResource(path.replace(".","/"));
            String file = url.getFile();
            File parentFile = new File(file);
            //子文件
            File[] subFiles = parentFile.listFiles();

            if (subFiles == null || subFiles.length == 0) {
                return;
            }
            for (File subFile :subFiles) {
                //如果是目录，递归扫描
                if (subFile.isDirectory()) {
                    scanPackage(path + "." + subFile.getName(),fileNames);
                } else {
                    fileNames.add(path + "." + subFile.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
