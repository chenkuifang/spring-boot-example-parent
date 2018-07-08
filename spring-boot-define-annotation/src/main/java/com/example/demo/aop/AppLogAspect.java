package com.example.demo.aop;

import java.lang.reflect.Method;

import com.example.demo.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author QuiFar
 * @version V1.0
 * @Description: 日志切面实现类
 * @date 2018年1月2日 下午8:24:26
 */
@Component
@Aspect
@Order(-1)
public class AppLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AppLogAspect.class);

    // 限定作用域在controller包下
    @Pointcut("execution(* com.example.demo.controller..*(..)) and @annotation(com.example.demo.annotation.Log)")
    //@Pointcut("@annotation(com.example.demo.annotation.Log)")
    public void pointcut() {
    }

    /**
     * 利用切面做日志记录
     * @param joinPoint
     */
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        String value = log.value();

        logger.info(value);
    }

    @After("pointcut()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("doAfter():{}", joinPoint.toString());
    }

    @AfterReturning("pointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        logger.info("返回后");
    }
}
