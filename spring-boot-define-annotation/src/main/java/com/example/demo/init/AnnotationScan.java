package com.example.demo.init;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.annotation.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 该bean可实现扫描指定文件规范做约束
 *
 * @author QuiFar
 * @version V1.0
 * @date 2018年1月2日 下午11:12:50
 */
@Component
public class AnnotationScan implements ApplicationListener<ContextRefreshedEvent> {

    List<String> packageNames = new ArrayList<String>();

    @Override
    @Log
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.err.println("启动项目开始2");
        scanPackage("com.example.demo.controller");
        try {
            filterAndInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterAndInstance() throws Exception {
        if (packageNames.size() <= 0) {
            return;
        }
        // 1.遍历包下的所有类
        for (String className : packageNames) {
            Class<?> cName = Class.forName(className.replace(".class", "").trim());
            if (cName.isAnnotationPresent(Controller.class)) {
                // 获取控制类下的所有方法
                Method[] methods = cName.getDeclaredMethods();
                boolean flag = isContailsAnnotation(methods, "com.example.demo.annotation.Log");

                if (!flag) {
                    // 关闭JVM
                    System.exit(0);
                    // throw new RuntimeException(cName.getSimpleName() + "的包含没有使用Log注解的方法");
                }

            }
        }

    }

    /**
     * 是否包含指定的注解名称
     *
     * @param annotationName 注解路径，如com.example.demo.annotation.Log
     * @return
     */
    private boolean isContailsAnnotation(Method[] methods, String annotationName) {
        boolean isValue = true;
        // 遍历类下的所有方法
        for (Method m : methods) {
            // 每个方法是否都包含某个注解
            boolean flag = false;
            if (m.isAnnotationPresent(RequestMapping.class) || m.isAnnotationPresent(GetMapping.class)
                    || m.isAnnotationPresent(PostMapping.class)) {
                // 遍历方法下的所有注解
                for (Annotation an : m.getAnnotations()) {
                    if (annotationName.equals(an.annotationType().getName())) {
                        flag = true;
                        break;
                    }
                }
                // 没有查到,直接跳出循环
                if (!flag) {
                    isValue = false;
                    break;
                }
            }
        }

        return isValue;
    }

    /**
     * 扫描指定包
     *
     * @param Package
     */
    private void scanPackage(String Package) {
        // 将所有的.转义获取对应的路径
        URL url = this.getClass().getClassLoader().getResource(replaceTo(Package));
        String pathFile = url.getFile();

        File file = new File(pathFile);
        String[] fileList = file.list();
        for (String path : fileList) {
            File eachFile = new File(pathFile + "/" + path);
            // 扫描到目录，继续递归扫描
            if (eachFile.isDirectory()) {
                scanPackage(Package + "." + eachFile.getName());
            } else {
                packageNames.add(Package + "." + eachFile.getName());
            }
        }
    }

    /**
     * 转义
     *
     * @param path
     * @return
     */
    private String replaceTo(String path) {
        return path.replaceAll("\\.", "/");
    }

}
