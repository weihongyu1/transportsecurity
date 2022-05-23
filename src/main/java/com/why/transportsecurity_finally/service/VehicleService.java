package com.why.transportsecurity_finally.service;

import com.github.pagehelper.PageInfo;
import com.why.transportsecurity_finally.entity.VehicleInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO 车辆信息管理
 * @Author why
 * @Date 2021/7/26 14:39
 * Version 1.0
 **/
public interface VehicleService {

    /**
     * 添加车辆信息
     * @param vehicleInfo
     */
    public boolean insertVehicle(VehicleInfo vehicleInfo);

    /**
     * 删除车辆信息
     * @param id
     * @return
     */
    public boolean deleteVehicle(Integer id);

    /**
     * 获取所有车辆信息
     * @return
     */
    public Map<Integer,List<VehicleInfo>> getAll(Integer pageNum);

    /**
     * 修改车辆信息
     * @param vehicleInfo
     * @return
     */
    public boolean updateVehicle(VehicleInfo vehicleInfo);

    /**
     * 搜索
     * @param search
     * @return
     */
    public List<VehicleInfo> search(String search);
}
