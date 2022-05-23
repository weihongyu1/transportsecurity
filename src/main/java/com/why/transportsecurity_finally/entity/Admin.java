package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 管理员实体类
 * @Author why
 * @Date 2021/7/24 16:09
 * Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户id
     */
    private String uId;
    /**
     * 姓名
     */
    private String uName;
    /**
     * 密码
     */
    private String uPwd;
    /**
     * 盐值
     */
    private String uSalt;
}
