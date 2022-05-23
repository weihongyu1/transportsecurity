package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description TODO 数据下载实体类
 * @Author why
 * @Date 2021/7/22 11:07
 * Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoExcelModel {
    private String vName;

    private String vNumber;

    private String vType;

    private String driverDegreeOfDamage;

    private String passengerDegreeOfDamage;

    private String date;

    private String time;

    private double lng;

    private double lat;

    private String address;

    private String collisionDirection;

    private String isBounce;

    private List<Double> adRAcceleration;

    private List<Double> adCAcceleration;
}
