package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 车辆事故信息实体类
 * @Author why
 * @Date 2021/7/21 10:27
 * Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccidentInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 时间
     */
    private long aDate;
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 处理状态，0表示未处理，1表示已处理
     */
    private int state;
    /**
     * 车辆基本信息id
     */
    private Integer vId;

}
