package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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
    @GetMapping("/list")
    public List<User> list() {
        return userService.list(new HashMap<>());
    }

    /**
     * 使用翻页插件简单的实现翻页功能
     * 1.开启翻页
     * 2.设定pageSize pageNum
     * <p>
     * 注意：1.如果存在一对多的情况下，为了保证返回的页数是正确的
     * 可以使用子查询的方式解决。这样会出现多次查询的情况
     * 2.当然也可以不使用分页插件，自己多写一个计算总页数的方法。这样就是两个查询即可
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/page")
    public PageInfo page(Integer pageSize, Integer pageNum) {
        // 在需要翻页的列表前加上静态的翻页开启即可
        PageHelper.startPage(pageSize, pageNum, true);

        List<UserDTO> list = userService.listPage();
        return new PageInfo(list);
    }

}
