package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.demo.common.Constants;

/**
 * @author QuiFar
 * @version V1.0
 * @Description: 登陆拦截器
 * @date 2017年11月19日 下午6:01:32
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String path = request.getServletPath();
        System.err.println(path);
        if (path.matches(Constants.NO_INTERCEPTOR_PATH)) {
            return true;
        } else {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute(Constants.SESSION_USER);
            if (obj == null) {
                response.sendRedirect(request.getContextPath() + "/login.html");
                return false;
            }

        }
        return true;
    }
}
