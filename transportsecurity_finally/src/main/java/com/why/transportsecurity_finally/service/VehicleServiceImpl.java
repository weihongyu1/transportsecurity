package com.why.transportsecurity_finally.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.why.transportsecurity_finally.entity.AccidentInfo;
import com.why.transportsecurity_finally.entity.TotalInfo;
import com.why.transportsecurity_finally.entity.VehicleInfo;
import com.why.transportsecurity_finally.mapper.AccidentInfoMapper;
import com.why.transportsecurity_finally.mapper.AxMapper;
import com.why.transportsecurity_finally.mapper.AyMapper;
import com.why.transportsecurity_finally.mapper.VehicleInfoMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description TODO 车辆信息管理实现类
 * @Author why
 * @Date 2021/7/26 14:40
 * Version 1.0
 **/
@Service
@Log
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleInfoMapper vehicleInfoMapper;

    @Override
    public boolean insertVehicle(VehicleInfo vehicleInfo) {
        if (vehicleInfo == null){
            log.warning("添加车辆信息，输入非法");
            return false;
        }
        List<VehicleInfo> vehicleByVNum = vehicleInfoMapper.getVehicleByVNum(vehicleInfo.getVNum());
        Iterator<VehicleInfo> iterator = vehicleByVNum.iterator();
        if (vehicleByVNum.size() > 0){
            log.warning("车辆已存在");
            return false;
        }
        vehicleInfoMapper.insertVehicle(vehicleInfo);
        return true;
    }

    @Autowired
    AxMapper axMapper;
    @Autowired
    AyMapper ayMapper;
    @Autowired
    AccidentInfoMapper accidentInfoMapper;

    @Override
    public boolean deleteVehicle(Integer id) {
        if (id == null){
            return false;
        }
        //查找事故aId
        List<AccidentInfo> accidentInfo = accidentInfoMapper.getAccidentInfoByVId(id);
        if (accidentInfo.size() >= 0){
            Iterator<AccidentInfo> iterator = accidentInfo.iterator();
            while (iterator.hasNext()) {
                AccidentInfo next =  iterator.next();
                if (next != null){
                    Integer aId = next.getId();
                    //删除ax，ay
                    axMapper.deleteAx(aId);
                    ayMapper.deleteAy(aId);
                    //删除事故信息
                    accidentInfoMapper.deleteAccident(id);
                }
            }
        }
        //删除车辆信息
        log.warning("删除车辆信息");
        vehicleInfoMapper.deleteVehicle(id);
        return true;
    }

    @Override
    public Map<Integer,List<VehicleInfo>> getAll(Integer pageNum) {
        Map<Integer,List<VehicleInfo>> map = new HashMap<>();
        Page<Object> page = PageHelper.startPage(pageNum, 10);
        List<VehicleInfo> vehicles = vehicleInfoMapper.getAll();
        Integer count = vehicleInfoMapper.getCount();
        map.put(count,vehicles);
        return map;
    }

    @Override
    public boolean updateVehicle(VehicleInfo vehicleInfo) {
        if (vehicleInfo == null){
            return false;
        }
        vehicleInfoMapper.updateVehicle(vehicleInfo);
        return true;
    }

    @Override
    public List<VehicleInfo> search(String search) {
        if (search == null) {
            return null;
        }
        List<VehicleInfo> vehicles = null;
        //分析search是车牌号还是车主姓名
        //车主姓名
        if (search.contains("-"))//车牌号
            vehicles= vehicleInfoMapper.getVehicleByVNum(search);
        else
            vehicles = vehicleInfoMapper.getVehicleByVName(search);
        if (vehicles.size() <= 0){
            return null;
        }
        return vehicles;
    }
}
