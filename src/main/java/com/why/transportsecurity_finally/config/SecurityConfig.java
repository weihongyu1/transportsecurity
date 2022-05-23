//package com.why.transportsecurity_finally.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * @ClassName：SecurityConfig
// * @Description：todo 配置spring security
// * @Author: why
// * @DateTime：2021/12/11 13:40
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //自定义登录页面
//        //表单登录配置
//        http.formLogin()
//                //自定义登录页面
//                .loginPage("/ts/accident/showLogin")
//                //自定义登录逻辑
//                .loginProcessingUrl("/ts/accident/login");
//
//        //关闭csrf防护
//        http.csrf().disable();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
