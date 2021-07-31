package com.why.transportsecurity_finally.utils;

import lombok.extern.java.Log;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @Description TODO cookie + session登录认证
 * @Author why
 * @Date 2021/7/24 17:07
 * Version 1.0
 **/
@Log
public class CookieSessionUtils {

    /**
     * 1.创建session
     * 2.创建cookie
     * @param request
     */
    public static void cookieAndSession(HttpServletRequest request, HttpServletResponse response, String uId){
        String uuid = UUID.randomUUID().toString();
        HttpSession session = request.getSession();
        byte[] key = Sm4Util.getBytes("whylovewyy", 16);
        byte[] bytes = null;
        try {
            bytes = Sm4Util.encryptEcbPkcs5Padding(uId.getBytes(), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute(uuid,bytes.toString());
        Cookie cookieName = new Cookie("name",uuid);
        Cookie cookieVal = new Cookie("val",bytes.toString());
        response.addCookie(cookieName);
        response.addCookie(cookieVal);
    }

    public static void deleteCookiesAndSession(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name")){
                cookie.setValue(null);
                response.addCookie(cookie);
            }
            if (cookie.getName().equals("val")){
                cookie.setValue(null);
                response.addCookie(cookie);
            }
        }
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
