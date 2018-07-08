package com.example.demo.controller;

import com.example.demo.annotation.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QuiFar
 * @version V1.0
 * @Description: index 控制器
 * @date 2018年1月2日 下午8:18:32
 */
@RestController
public class IndexController {

    @Log("日志")
    @GetMapping("/index")
    public String index() {
        System.err.println("index");
        return "hello world";
    }

    @PostMapping("/test")
    public String test() {
        System.err.println("test");
        return "hello test";
    }
}
