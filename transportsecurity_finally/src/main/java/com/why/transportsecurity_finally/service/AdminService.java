package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.entity.Admin;
import com.why.transportsecurity_finally.entity.AdminAll;
import com.why.transportsecurity_finally.entity.AdminInfo;

/**
 * @Description TODO 管理员登录注册
 * @Author why
 * @Date 2021/7/24 16:17
 * Version 1.0
 **/
public interface AdminService {

    /**
     * 登录
     * @param uId
     * @param pwd
     * @return
     */
    public boolean login(String uId,String pwd);

    /**
     * 返回用户姓名
     * @param uId
     * @return
     */
    public String loginName(String uId);

    /**
     * 注册
     * @param admin
     */
    public int register(Admin admin);

    /**
     * 根据uId获取admin
     * @param uId
     * @return
     */
    public Admin getAdmin(String uId);

    /**
     * 找回密码
     * @param uId
     * @param toAddress
     */
    public void findPwd(String uId,String toAddress);

    /*****************************************个人信息***********************************/

    /**
     * 根据管理员id获取管理员信息
     * @param uId
     * @return
     */
    public AdminAll getAdminInfo(Integer uId);

    /**
     * 添加管理员信息
     * @param adminInfo
     * @return
     */
    public boolean insertAdminInfo(AdminInfo adminInfo);

    /**
     * 更新数据
     * @param adminAll
     * @return
     */
    public boolean updateAdminInfo(AdminAll adminAll);

    /**
     * 修改密码
     * @param uId
     * @param pwdNew
     * @param pwdOld
     * @return
     */
    public boolean updatePwd(Integer uId,String pwdNew,String pwdOld);

    /**
     * 修改密码，找回
     * @param uId
     * @param pwdNew
     * @return
     */
    public boolean updatePwdFind(String uId,String pwdNew);
}
