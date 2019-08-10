package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 控制器全局异常捕获处理器
 *
 * @author: quifar
 * @version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public Object handle(Exception e) {
        log.error("全局异常:{}", e.getMessage());
        return e.getMessage();
    }
}
