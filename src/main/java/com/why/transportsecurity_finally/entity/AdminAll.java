package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Description TODO 封装个人中心信息
 * @Author why
 * @Date 2021/7/27 11:11
 * Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAll {
    /**
     * 用户id
     */
    private String uIds;
    /**
     * 姓名
     */
    private String uName;
    /**
     * id
     */
    private Integer id;
    /**
     * 电话号
     */
    private String uPhone;
    /**
     * 邮箱号
     */
    private String eMail;
    /**
     * 地址
     */
    private String uAddress;
    /**
     * 出生日期
     */
    private String uBirth;
    /**
     * 入职日期
     */
    private String uDate;
    /**
     * 外键uId
     */
    private Integer uId;
}
