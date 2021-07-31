package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 纵向加速的ay
 * @Author why
 * @Date 2021/7/21 10:33
 * Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ay {
    /**
     * id
     */
    private Integer id;
    /**
     * ay
     */
    private double accelerationY;
    /**
     * 事故信息id
     */
    private Integer aId;
}
