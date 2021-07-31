package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.entity.Admin;
import com.why.transportsecurity_finally.entity.AdminAll;
import com.why.transportsecurity_finally.entity.AdminInfo;
import com.why.transportsecurity_finally.mapper.AdminMapper;
import com.why.transportsecurity_finally.utils.MailUtils;
import com.why.transportsecurity_finally.utils.SaltUtil;
import com.why.transportsecurity_finally.utils.Sm3Util;
import com.why.transportsecurity_finally.utils.Sm4Util;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO 管理员登录注册实现类
 * @Author why
 * @Date 2021/7/24 16:20
 * Version 1.0
 **/
@Service
@Log
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    /**
     * 用户登录
     * @param uId
     * @param pwd
     * @return
     */
    @Override
    public boolean login(String uId, String pwd) {
        if (uId == null || pwd == null){
            log.warning("用户名或密码为空");
            return false;
        }
        Admin adminById = adminMapper.getAdminById(uId);
        if (adminById == null){
            log.warning("用户ID输入错误");
            return false;
        }
        String pwdNew = SaltUtil.merge(pwd, adminById.getUSalt());
        if (pwdNew.equals(adminById.getUPwd())) {
            log.info("登录成功");
            return true;
        }
        log.info("密码错误");
        return false;
    }

    /**
     * 1：输入非法，2：用户已存在，3：注册成功
     * @param admin
     * @return
     */
    @Override
    public int register(Admin admin) {
        if (admin == null){
            log.warning("输入非法");
            return 1;
        }
        //查询用户是否存在
        Admin adminById = adminMapper.getAdminById(admin.getUId());
        if (adminById != null){
            log.warning("用户已存在");
            return 2;
        }
        //处理密码
        String salt = Sm3Util.creatSalt();
        String pwdNew = SaltUtil.merge(admin.getUPwd(), salt);
        admin.setUPwd(pwdNew);
        admin.setUSalt(salt);
        adminMapper.insertAdmin(admin);
        log.info("注册成功");
        return 0;
    }

    @Override
    public String loginName(String uId) {
        Admin adminById = adminMapper.getAdminById(uId);
        return adminById.getUName();
    }

    @Override
    public Admin getAdmin(String uId) {
        if (uId == null){
            log.warning("获取admin失败");
            return new Admin();
        }
        Admin adminById = adminMapper.getAdminById(uId);
        return adminById;
    }

    @Override
    public void findPwd(String uId, String toAddress) {
        Admin adminById = adminMapper.getAdminById(uId);
        if (adminById == null){
            log.warning("找回密码，该账号未注册");
            return;
        }
        String salt = Sm3Util.creatSalt();
        String merge = SaltUtil.merge("toAddress", salt);
        adminById.setUSalt(salt);
        adminById.setUPwd(merge);
        adminMapper.updateAdminPwd(adminById);
        MailUtils.sendMail(toAddress);
    }

    /*****************************************个人信息***********************************/

    @Override
    public AdminAll getAdminInfo(Integer uId) {
        if (uId == null){
            log.warning("获取管理员信息输入为空！");
            return new AdminAll();
        }
        Admin admin = adminMapper.getAdminByIdPre(uId);
        if (admin == null){
            log.warning("获取管理员信息，该管理员不存在！");
            return new AdminAll();
        }
        AdminInfo adminInfo = adminMapper.getAdminInfoByUId(uId);
        AdminAll adminAll = new AdminAll();
        adminAll.setUIds(admin.getUId());
        adminAll.setUName(admin.getUName());
        if (adminInfo.getId() == null){
            adminAll.setId(null);
        }else {
            adminAll.setId(adminInfo.getId());
        }
        if (adminInfo.getUPhone() == null){
            adminAll.setUPhone(null);
        }else {
            adminAll.setUPhone(adminInfo.getUPhone());
        }
        if (adminInfo.getUEmail() == null) {
            adminAll.setEMail(null);
        }else {
            adminAll.setEMail(adminInfo.getUEmail());
        }
        if (adminInfo.getUAddress() == null) {
            adminAll.setUAddress(null);
        }else {
            adminAll.setUAddress(adminInfo.getUAddress());
        }
        if (adminInfo.getUBirth() == null) {
            adminAll.setUBirth(null);
        }else {
            adminAll.setUBirth(adminInfo.getUBirth());
        }
        if (adminInfo.getUDate() == null) {
            adminAll.setUDate(null);
        }else {
            adminAll.setUDate(adminInfo.getUDate());
        }
        adminAll.setUId(adminInfo.getUId());
        return adminAll;
    }

    @Override
    public boolean insertAdminInfo(AdminInfo adminInfo) {
        if (adminInfo.getUId() == null){
            log.warning("添加信息失败");
            return false;
        }
        adminMapper.insertAdminInfo(adminInfo);
        return true;
    }

    @Override
    public boolean updateAdminInfo(AdminAll adminAll) {
        if (adminAll == null){
            log.warning("更新管理员信息错误");
            return false;
        }
        Admin admin = new Admin(null, adminAll.getUIds(), adminAll.getUName(), null, null);
        //                                  Integer id, String uPhone, String uEmail, String uAddress, Date uBirth, Date uDate, Integer uId
        AdminInfo adminInfo = new AdminInfo(adminAll.getId(), adminAll.getUPhone(), adminAll.getEMail(), adminAll.getUAddress(), adminAll.getUBirth(), adminAll.getUDate(), adminAll.getUId());
        adminMapper.updateAdmin(admin);
        adminMapper.updateAdminInfo(adminInfo);
        return true;
    }

    @Override
    public boolean updatePwd(Integer uId, String pwdNew, String pwdOld) {
        if (uId == null || pwdNew == null || pwdOld == null){
            log.warning("修改密码失败");
            return false;
        }
        Admin admin = adminMapper.getAdminByIdPre(uId);
        if (admin == null){
            log.warning("修改密码，用户不存在");
            return false;
        }
        String identifyPwd = SaltUtil.merge(pwdOld, admin.getUSalt());
        if (!identifyPwd.equals(admin.getUPwd())){
            log.warning("用户密码输入错误");
            return false;
        }
        String salt = Sm3Util.creatSalt();
        String newPwd = SaltUtil.merge(pwdNew, salt);
        admin.setUPwd(newPwd);
        admin.setUSalt(salt);
        adminMapper.updateAdminPwd(admin);
        return true;
    }

    @Override
    public boolean updatePwdFind(String uId, String pwdNew) {
        Admin adminById = adminMapper.getAdminById(uId);
        if (adminById == null){
            return false;
        }
        String salt = Sm3Util.creatSalt();
        String merge = SaltUtil.merge(pwdNew, salt);
        adminById.setUPwd(merge);
        adminById.setUSalt(salt);
        adminMapper.updateAdminPwd(adminById);
        return true;
    }
}
