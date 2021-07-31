package com.why.transportsecurity_finally.filter;

import com.why.transportsecurity_finally.utils.CookieSessionUtils;
import lombok.extern.java.Log;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @Description TODO 拦截器，登录检查
 * @Author why
 * @Date 2021/7/24 17:16
 * Version 1.0
 **/
@Log
public class LoginFilter implements HandlerInterceptor {

    /**
     * 目标方法执行前，登录检查
     *  1. 配置好拦截器拦截的请求
     *  2. 把拦截器配置放在容器中
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginFilter拦截的请求："+request.getRequestURI());
        /**
         * 登录检查逻辑
         */
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            log.info("首次登录");
            response.sendRedirect("/ts/accident/showLogin");
            return false;
        }

        String ssid = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name") && session.getAttribute(cookie.getValue()) != null){
               ssid = (String) session.getAttribute(cookie.getValue());
            }
            if (cookie.getName().equals("val") && cookie.getValue().equals(ssid)){
                log.info(request.getRequestURL() + "登录验证成功");
                //验证成功放行
                return true;
            }
        }
        log.warning("未登录或session已失效");
        response.sendRedirect("/ts/accident/showLogin");
        return false;
    }

    /**
     * 目标方法执行后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 页面渲染后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
