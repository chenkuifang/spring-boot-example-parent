package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user controller
 *
 * @author quifar
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 列表展示
     *
     * @return
     */
    @RequestMapping(path = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public List<User> list() {
        Map<String, Object> params = new HashMap<>();
        return userService.list(params);
    }
}
