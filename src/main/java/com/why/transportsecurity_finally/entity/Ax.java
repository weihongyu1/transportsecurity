package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 横向加速度实体类
 * @Author why
 * @Date 2021/7/21 10:31
 * Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ax {
    /**
     * id
     */
    private Integer id;
    /**
     * 横向加速度ax
     */
    private double accelerationX;
    /**
     * 车辆事故信息id
     */
    private Integer aId;
}
