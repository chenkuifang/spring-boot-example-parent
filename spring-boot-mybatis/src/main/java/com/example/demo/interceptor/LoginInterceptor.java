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
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String path = request.getServletPath();
        if (path.matches(Constants.NO_INTERCEPTOR_PATH)) {
            return true;
        } else {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute(Constants.SESSION_USER);
            if (obj == null) {
                response.sendRedirect(request.getContextPath() + "/login.html");
                return false;
            }

            // 这里也可以使用jwt校验token是否有效来做权限校验
        }
        return true;
    }
}
