package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author QuiFar
 * @version V1.0
 * @Description: 测试控制类
 * @date 2017年11月18日 下午4:21:41
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public void test() {
        System.err.println("进入控制器");
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/page")
    public List<User> pageTest() {
        return userService.list(null);
    }

    @PostMapping("/pagePost")
    public List<User> pagePost() {
        return userService.list(null);
    }

}
