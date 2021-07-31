package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 车辆基本信息实体类
 * @Author why
 * @Date 2021/7/21 10:23
 * Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 车主姓名
     */
    private String vName;
    /**
     * 车牌号
     */
    private String vNum;
    /**
     * 车辆类型
     */
    private String vType;

}
