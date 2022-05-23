package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Description TODO 管理员信息
 * @Author why
 * @Date 2021/7/27 10:42
 * Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfo {
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
    private String uEmail;
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
