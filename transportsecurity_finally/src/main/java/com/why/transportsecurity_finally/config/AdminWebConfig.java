package com.why.transportsecurity_finally.config;

import com.why.transportsecurity_finally.filter.LogFilter;
import com.why.transportsecurity_finally.filter.LoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description TODO 登陆检查filter配置
 * @Author why
 * @Date 2021/7/24 17:21
 * Version 1.0
 **/
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    /**
     * 登陆检查filter配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginFilter())
                .addPathPatterns("/**") //拦截的请求
                .excludePathPatterns("/ts/accident/showLogin","/ts/accident/login","/ts/accident/showRegister"
                        ,"/ts/accident/register","/ts/accident/showFindPre","/ts/accident/findPwd","/ts/accident/sendPhone"
                        ,"/css/**","/img/**","/mapper/**","/js/**","/music/**","/favicon.ico","/error/**","/druid/login.html");//放行的请求

        registry.addInterceptor(new LogFilter()).addPathPatterns("/**");
    }
}
