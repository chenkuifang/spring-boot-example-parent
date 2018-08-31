package com.example.demo.controller;

import com.example.demo.common.Constants;
import com.example.demo.common.JsonResult;
import com.example.demo.common.MDUtils;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 登陆控制类
 *
 * @author QuiFar
 * @version V1.0
 */
@Controller
public class LoginController {

    @Autowired
    private UserService UserService;

    /**
     * 获取登陆初始化页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登陆校验
     *
     * @param userName 用户名
     * @param password 登陆密码
     * @return code 100 成功，101 失败
     */
    @PostMapping("/loginPost")
    @ResponseBody
    public JsonResult loginPost(HttpSession session, @RequestParam("userName") String userName,
                                @RequestParam("password") final String password) {
        JsonResult result = JsonResult.getInstance();

        String code = Constants.RESULT_CODE_FAIL;
        String msg = "用户名或密码错误!";
        User user = UserService.getByUserName(userName);

        if (user != null) {
            String passwordStr = "";
            try {
                passwordStr = MDUtils.encodeMD5(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (user.getPassword().equals(passwordStr)) {
                code = Constants.RESULT_CODE_SUCCESS;
                msg = Constants.RESULT_SUCCESS_DESCRIPTION;

                session.setAttribute(Constants.SESSION_USER, user);
            }
        }

        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
