package com.why.transportsecurity_finally.controller;

import com.why.transportsecurity_finally.entity.Admin;
import com.why.transportsecurity_finally.entity.AdminAll;
import com.why.transportsecurity_finally.entity.AdminInfo;
import com.why.transportsecurity_finally.service.AdminServiceImpl;
import com.why.transportsecurity_finally.utils.CookieSessionUtils;
import com.why.transportsecurity_finally.utils.PhoneUtils;
import com.why.transportsecurity_finally.utils.RandomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

/**
 * @Description TODO 管理员信息控制器
 * @Author why
 * @Date 2021/8/15 11:15
 * Version 1.0
 **/
@Controller
@RequestMapping("/ts")
@Slf4j
@Api("管理员信息管理")
public class AdminController {

    @Autowired
    AdminServiceImpl adminService;

    @RequestMapping("/accident/showLogin")
    public String showLogin(){
        return "adminPage/signIn";
    }

    @RequestMapping("/accident/showRegister")
    public String showRegister(){
        return "adminPage/register";
    }

    @ApiOperation("登录")
    @RequestMapping("/accident/login")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response){
        String uId = request.getParameter("userId");
        String pwd = request.getParameter("pwd");
        boolean login = adminService.login(uId, pwd);
        if (!login){
            model.addAttribute("loginError","用户名或密码输入错误");
            return "adminPage/signIn";
        }
        CookieSessionUtils.cookieAndSession(request,response,uId);
        HttpSession session = request.getSession();
        session.setAttribute("login",adminService.loginName(uId));
        session.setAttribute("loginAdmin",uId);
        return "redirect:/ts/accident/warning";
    }

    @ApiOperation("注册")
    @RequestMapping("/accident/register")
    public String register(Model model,HttpServletRequest request){
        Admin admin = new Admin();
        String uId = request.getParameter("uId");
        String uName = request.getParameter("uName");
        String uPhone = request.getParameter("uPhone");
        String pwd = request.getParameter("pwd");

        admin.setUId(uId);
        admin.setUName(uName);
        admin.setUPwd(pwd);
        int register = adminService.register(admin);
        if (register == 1){
            model.addAttribute("registerError","输入不能为空");
            return "adminPage/register";
        }
        if (register == 2){
            model.addAttribute("registerError","用户名已存在");
            return "adminPage/register";
        }
        Admin admin1 = adminService.getAdmin(uId);
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setUId(admin1.getId());
        adminInfo.setUPhone(uPhone);
        adminService.insertAdminInfo(adminInfo);
        return "redirect:/ts/accident/showLogin";
    }

    @ApiOperation("注销账号")
    @RequestMapping("/accident/logOut")
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        log.info("正在登出...");
        CookieSessionUtils.deleteCookiesAndSession(request,response);
        return "redirect:/ts/accident/showLogin";
    }

    /**
     *个人中心
     * @return
     */
    @ApiOperation("个人中心")
    @RequestMapping("/accident/personal")
    public String showPerson(HttpServletRequest request,Model model){
        String uIds = (String) request.getSession().getAttribute("loginAdmin");
        Admin admin = adminService.getAdmin(uIds);
        if (admin == null){
            log.warn("未登录，请重新登录！");
            return "redirect:/ts/accident/showLogin";
        }
        AdminAll adminInfo = adminService.getAdminInfo(admin.getId());
        model.addAttribute("adminAll",adminInfo);
        return "adminPage/personalCenter";
    }

    /**
     * 基本信息修改
     * @param request
     * @return
     */
    @ApiOperation("管理员基本信息修改")
    @RequestMapping("/accident/personalUpdate")
    public String updateInfo(HttpServletRequest request){
        String uName = request.getParameter("uName");
        String uIds = request.getParameter("uIds");
        String uPhone = request.getParameter("uPhone");
        String uEmail = request.getParameter("uEmail");
        String uAddress = request.getParameter("uAddress");
        String uBirth = request.getParameter("uBirth");
        String uDate = request.getParameter("uDate");
        String id = request.getParameter("id");
        String uId = request.getParameter("uId");
        AdminAll adminAll = new AdminAll(uIds, uName, Integer.valueOf(id), uPhone, uEmail, uAddress, uBirth, uDate, Integer.valueOf(uId));
        boolean b = adminService.updateAdminInfo(adminAll);
        if (!b){
            return "redirect:/ts/accident/personal";
        }
        return "redirect:/ts/accident/personal";
    }

    @ApiOperation("管理员设置新密码")
    @RequestMapping("/accident/newPwd")
    public String updatePwd(HttpServletRequest request){
        String uId = request.getParameter("uId");
        String oldPwd = request.getParameter("uOldPwd");
        String pwdNew = request.getParameter("uNewPwdPre");
        boolean b = adminService.updatePwd(Integer.valueOf(uId), pwdNew, oldPwd);
        if (!b){
            return "redirect:/ts/accident/personal";
        }
        return "redirect:/ts/accident/personal";
    }

    @RequestMapping("/accident/showFindPre")
    public String findPwdPre(){
        return "adminPage/findPwd";
    }

    /**
     * 发送验证码
     * @param phone
     * @param uId
     */
    @ApiOperation("密码找回发送验证码")
    @RequestMapping(value = "/accident/sendPhone",method = RequestMethod.GET)
    @ResponseBody
    public void add(@PathParam("phone") String phone, @PathParam("uId") String uId){
        if (phone == null){
            log.warn("电话号码输入为空！");
            return;
        }
        if (uId == null) {
            log.warn("uId输入为空");
            return;
        }
        //查询是否存在用户
        Admin admin = adminService.getAdmin(uId);
        if (admin == null){
            log.warn("密码找回，用户不存在");
            return;
        }
        AdminAll adminInfo = adminService.getAdminInfo(admin.getId());
        if (!adminInfo.getUPhone().equals(phone)){
            log.warn("输入电话未绑定");
            return;
        }
        //存在用户，修改密码，发送短信
        String newPwd = RandomUtils.getRandom();
        boolean b = adminService.updatePwdFind(uId, newPwd);
        if (!b){
            log.warn("找回密码，修改密码失败");
            return;
        }
        String[] phoneNum = {phone};
        String[] params = {newPwd};
        PhoneUtils.sendPhoneMail(phoneNum,params);
    }

    /**
     * 根据验证码登录
     * @param request
     * @param model
     * @return
     */
    @ApiOperation("验证码登录")
    @RequestMapping("/accident/findPwd")
    public String findPwd(HttpServletRequest request,Model model,HttpServletResponse response){
        String uId = request.getParameter("uId");
        String toAddress = request.getParameter("toPwd");

        boolean login = adminService.login(uId, toAddress);
        if (!login){
            log.warn("验证码错误");
            model.addAttribute("msg","验证码错误");
            return "adminPage/findPwd";
        }
        CookieSessionUtils.cookieAndSession(request,response,uId);
        HttpSession session = request.getSession();
        session.setAttribute("login",adminService.loginName(uId));
        session.setAttribute("loginAdmin",uId);
        return "redirect:/ts/accident/warning";
    }
}
