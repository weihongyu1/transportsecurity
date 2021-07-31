package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 封装加速度信息
 * @Author why
 * @Date 2021/7/22 10:50
 * Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acceleration {
    private double ax;
    private double ay;
}
