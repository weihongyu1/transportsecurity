package com.why.transportsecurity_finally.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO 封装统计事故信息
 * @Author why
 * @Date 2021/7/21 11:43
 * Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalInfo {
    /**
     * 车辆id
     */
    private Integer vId;
    /**
     * 事故id
     */
    private Integer aId;
    /**
     * 车牌号
     */
    private String vNum;
    /**
     * 车主姓名
     */
    private String vName;
    /**
     * 车辆类型
     */
    private String vType;
    /**
     * 日期
     */
    private String date;
    /**
     * 处理状态,true：已处理，false：未处理
     */
    private boolean status;
}
