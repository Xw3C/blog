package com.cxw.interceptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    //preHandle方法，在请求未到达目的地之前，预处理操作
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //判断session里面是否有user，user==null则表明未登录，重定向到登录页面
        if (request.getSession().getAttribute("user")== null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
