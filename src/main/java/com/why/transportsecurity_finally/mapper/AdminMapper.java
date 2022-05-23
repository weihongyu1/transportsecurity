package com.why.transportsecurity_finally.mapper;

import com.why.transportsecurity_finally.entity.Admin;
import com.why.transportsecurity_finally.entity.AdminInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO 管理员mapper
 * @Author why
 * @Date 2021/7/24 16:11
 * Version 1.0
 **/
@Mapper
public interface AdminMapper {

    /*****************************************登录注册***********************************/


    /**
     * 根据uId查询管理员信息
     * @param uId
     * @return
     */
    public Admin getAdminById(String uId);

    /**
     * 根据id查询管理员
     * @param id
     * @return
     */
    public Admin getAdminByIdPre(Integer id);

    /**
     * 插入管理员信息
     * @param admin
     * @return
     */
    public void insertAdmin(Admin admin);

    /**
     * 更新数据
     * @param admin
     */
    public void updateAdmin(Admin admin);

    /**
     * 更新密码
     * @param admin
     */
    public void  updateAdminPwd(Admin admin);

    /*****************************************个人信息***********************************/

    /**
     * 获取adminInfo
     * @param uId
     * @return
     */
    public AdminInfo getAdminInfoByUId(Integer uId);

    /**
     * 添加管理员信息
     * @param adminInfo
     * @return
     */
    public void insertAdminInfo(AdminInfo adminInfo);

    /**
     * 更新管理员数据
     * @param adminInfo
     */
    public void updateAdminInfo(AdminInfo adminInfo);
}
