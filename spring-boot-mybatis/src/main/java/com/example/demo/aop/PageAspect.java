package com.example.demo.aop;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * pageHelper 切面
 * 约定好需要翻页方法已listPage/page开头
 * 进行增强
 *
 * @author: Quifar
 * @version: 1.0
 */
@Aspect
@Component
@Slf4j
public class PageAspect {

    /**
     * 翻页参数拦截路径
     */
    private static final String PAGE_INTERCEPTOR_PATH = "page";

    //@Pointcut(value = "execution(* *..com.example.demo.controller..*(..))")\
    @Pointcut(value = "this(com.example.demo.controller.TestController)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getName().contains(PAGE_INTERCEPTOR_PATH)) {
            if (!"GET".equalsIgnoreCase(request.getMethod())) {
                throw new IllegalArgumentException("请使用Get请求。");
            }

            String pageNum = request.getParameter("pageNum");
            String pageSize = request.getParameter("pageSize");
            if (StringUtils.isBlank(pageNum) || StringUtils.isBlank(pageSize)) {
                throw new IllegalArgumentException("翻页必须包含pageNum,pageSize参数。");
            }

            PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        }
    }
}
