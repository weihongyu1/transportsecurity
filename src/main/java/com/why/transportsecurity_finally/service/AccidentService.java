package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.entity.DetailInfo;
import com.why.transportsecurity_finally.entity.TotalInfo;
import com.why.transportsecurity_finally.entity.VehicleInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO 事故信息Service
 * @Author why
 * @Date 2021/7/21 11:42
 * Version 1.0
 **/
@Service
public interface AccidentService {
    /**
     * 返回统计信息
     *  1. 查询tbl_accident_info表
     *  2. 根据v_id查询tbl_vehicle_info表
     * @return
     */
    public List<TotalInfo> totalInfo();

    /**
     * 返回事故细节信息
     * @param VId 车辆基础信息id
     * @param aId 事故信息id
     * @return
     */
    public DetailInfo detailInfo(Integer VId, Integer aId);

    /**
     * 查询车辆基本信息
     * @param vId
     * @return
     */
    public VehicleInfo vehicleInfo(Integer vId);

    /**
     * 查询加速度ax
     * @param aId
     * @return
     */
    public List<Double> ax(Integer aId);

    /**
     * 查询加速度ay
     * @param aId
     * @return
     */
    public List<Double> ay(Integer aId);

    /**
     * 处理事故
     * @param aID
     */
    public void solveAccident(Integer aID);

    /**
     * 搜索功能
     * @param search
     * @return
     */
    public List<TotalInfo> search(String search);

    /**
     * 添加数据
     * @param info
     */
    public void insert(String info);
}
