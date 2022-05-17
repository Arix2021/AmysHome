package com.alix.amypets.interceptor;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/*
    这是一个登录拦截器
*/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 请求拦截
     * @param request 请求
     * @param response 响应
     * @param handler 处理
     * @return true 放行 false 拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 基本拦截情况
        System.out.println("*--------------- 拦截情况 ----------------*");
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) System.out.println("请求者：" + user.getUsername());
        if (user != null) System.out.println("请求者级别：" + user.getType());
        System.out.println(request.getRequestURL());
        System.out.println(request.getMethod());
        System.out.println(new Date());
        System.out.println("*-----------------------------------------*");

        // 通过session检测登录
        if (request.getSession().getAttribute("user") != null) { // 已登录
            if (request.getRequestURL().toString().contains("admin")) { // 访问admin
                if (user.getType() <= 1) {
                    System.out.println("管理员" + user.getUsername() + "请求admin进入拦截，放行");
                    return true;
                } else {
                    System.out.println("普通用户" + user.getUsername() + "请求admin进入拦截，不放行");
                    response.sendRedirect("/web/zone/zone.html");
                    return false;
                }
            } else { // 访问普通资源
                System.out.println("普通用户" + user.getUsername() + "请求普通资源进入拦截，放行");
                return true;
            }
        } else { // 未登录
            if (request.getRequestURL().toString().contains("admin")) {
                response.sendRedirect("/web/admin/login.html");
            } else {
                response.sendRedirect("/web/user/account.html");
            }
            System.out.println("用户未登录进入拦截，不放行");
            return false;
        }
    }
}
