package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 封装展示细节信息
 * @Author why
 * @Date 2021/7/21 11:46
 * Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailInfo {
    /**
     * 驾驶员损伤程度
     */
    private String driverDegreeOfDamage;
    /**
     * 后排乘客损伤程度
     */
    private String passengerDegreeOfDamage;
    /**
     * 日期
     */
    private String date;
    /**
     * 时间
     */
    private String time;
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 地址
     */
    private String address;
    /**
     * 碰撞方向
     */
    private String direction;
    /**
     * 安全气囊是否弹开
     */
    private String isBounce;
    /**
     * 处理状态，true，已处理，false未处理
     */
    private boolean state;
}
