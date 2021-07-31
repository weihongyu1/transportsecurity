package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.mapper.AccidentInfoMapper;
import com.why.transportsecurity_finally.mapper.AxMapper;
import com.why.transportsecurity_finally.mapper.AyMapper;
import com.why.transportsecurity_finally.mapper.VehicleInfoMapper;
import com.why.transportsecurity_finally.entity.AccidentInfo;
import com.why.transportsecurity_finally.entity.DetailInfo;
import com.why.transportsecurity_finally.entity.TotalInfo;
import com.why.transportsecurity_finally.entity.VehicleInfo;
import com.why.transportsecurity_finally.utils.AccidentUtils;
import com.why.transportsecurity_finally.utils.BaiduMapUtils;
import com.why.transportsecurity_finally.utils.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description TODO 事故处理实现类
 * @Author why
 * @Date 2021/7/21 11:54
 * Version 1.0
 **/
@Service
public class AccidentServiceImpl implements AccidentService {
    @Autowired
    VehicleInfoMapper vehicleInfoMapper;
    @Autowired
    AccidentInfoMapper accidentInfoMapper;
    @Autowired
    AxMapper axMapper;
    @Autowired
    AyMapper ayMapper;

    @Override
    public List<TotalInfo> totalInfo() {
        List<TotalInfo> totalInfos = new ArrayList<>();

        //查询未处理数据
        List<AccidentInfo> allAccidentInfoUnSolve = accidentInfoMapper.getAllAccidentInfoUnSolve();
        //处理未处理数据
        for (AccidentInfo accidentInfo : allAccidentInfoUnSolve) {
            //Integer vId, Integer aId, String vNum, String name, String vType, String date, boolean status
            TotalInfo totalInfo = new TotalInfo();
            //设置vId，aId
            totalInfo.setVId(accidentInfo.getVId());
            totalInfo.setAId(accidentInfo.getId());
            //根据vId查询车辆信息
            VehicleInfo vehicle = vehicleInfoMapper.getVehicle(accidentInfo.getVId());
            //设置vNum
            totalInfo.setVNum(vehicle.getVNum());
            //设置vName
            totalInfo.setVName(vehicle.getVName());
            //设置vType
            totalInfo.setVType(vehicle.getVType());
            //处理日期,查询时间
            long l = DateFormatUtils.dateLong();
            String dateTime = DateFormatUtils.myFormatTime(l);
            String[] s = dateTime.split(" ");
            totalInfo.setDate(s[0]);
            //设置处理状态
            totalInfo.setStatus(false);
            totalInfos.add(totalInfo);
        }

        //查询已处理数据
        List<AccidentInfo> allAccidentInfoSolve = accidentInfoMapper.getAllAccidentInfoSolve();
        //处理已处理数据
        for (AccidentInfo accidentInfo : allAccidentInfoSolve) {
            //Integer vId, Integer aId, String vNum, String name, String vType, String date, boolean status
            TotalInfo totalInfo = new TotalInfo();
            //设置vId，aId
            totalInfo.setVId(accidentInfo.getVId());
            totalInfo.setAId(accidentInfo.getId());
            //根据vId查询车辆信息
            VehicleInfo vehicle = vehicleInfoMapper.getVehicle(accidentInfo.getVId());
            //设置vNum
            totalInfo.setVNum(vehicle.getVNum());
            //设置vName
            totalInfo.setVName(vehicle.getVName());
            //设置vType
            totalInfo.setVType(vehicle.getVType());
            //处理日期,查询时间
            long l = DateFormatUtils.dateLong();
            String dateTime = DateFormatUtils.myFormatTime(l);
            String[] s = dateTime.split(" ");
            totalInfo.setDate(s[0]);
            //设置处理状态
            totalInfo.setStatus(true);
            totalInfos.add(totalInfo);
        }
        //添加到集合
        return totalInfos;
    }

    @Override
    public DetailInfo detailInfo(Integer VId, Integer aId) {
        if (VId == null || aId == null){
            return new DetailInfo();
        }
        //创建事故信息封装对象
        DetailInfo detailInfo = new DetailInfo();
        //根据vId查询车辆基本信息
        VehicleInfo vehicle = vehicleInfoMapper.getVehicle(VId);
        //根据aId查询事故信息
        AccidentInfo accidentInfoById = accidentInfoMapper.getAccidentInfoById(aId);
        //根据aId查询加速度ax
        List<Double> ax = axMapper.getAx(aId);
        //根据aId查询加速度ay
        List<Double> ay = ayMapper.getAy(aId);

        //String degreeOfDamage,passengerDegreeOfDamage, String date, String time, double lng, double lat, String address, String direction, String isBounce
        /**
         * 计算损伤程度
         */
        //计算vx
        double vx = AccidentUtils.vx(ax);
        //计算vy
        double vy = AccidentUtils.vy(ay);
        //计算pdof
        double pdof = AccidentUtils.pdof(vx, vy);
        //计算碰撞方向
        int direction = AccidentUtils.direction(pdof);
        switch (direction){
            case 1:
                detailInfo.setDirection("正面碰撞");
                break;
            case 2:
                detailInfo.setDirection("左侧碰撞");
                break;
            case 3:
                detailInfo.setDirection("追尾碰撞");
                break;
            case 4:
                detailInfo.setDirection("右侧碰撞");
                break;
            default:
                break;
        }
        //计算驾驶员损伤程度
        boolean driverDegreeOfDamage = AccidentUtils.driverDegreeOfDamage(direction, true, vx);
        //计算后排乘客损伤程度
        boolean passengerDegreeOfDamage = AccidentUtils.passengerDegreeOfDamage(vx);
        if (driverDegreeOfDamage){
            detailInfo.setDriverDegreeOfDamage("严重损伤");
        }else {
            detailInfo.setDriverDegreeOfDamage("一般损伤");
        }
        if (passengerDegreeOfDamage){
            detailInfo.setPassengerDegreeOfDamage("严重损伤");
        }else {
            detailInfo.setPassengerDegreeOfDamage("一般损伤");
        }

        /**
         * 处理日期时间
         */
        long l = DateFormatUtils.dateLong();
        String dateTime = DateFormatUtils.myFormatTime(l);
        String[] dateAndTime = dateTime.split(" ");
        detailInfo.setDate(dateAndTime[0]);
        detailInfo.setTime(dateAndTime[1]);

        /**
         * 处理经纬度，地址信息
         */
        double lng = accidentInfoById.getLng();
        double lat = accidentInfoById.getLat();
        String address = BaiduMapUtils.getAddress(lng, lat);
        detailInfo.setLng(lng);
        detailInfo.setLat(lat);
        detailInfo.setAddress(address);
        detailInfo.setIsBounce("弹开");

        if (accidentInfoById.getState() == 0){//未处理
            detailInfo.setState(false);
        }else {
            detailInfo.setState(true);
        }
        return detailInfo;
    }

    @Override
    public VehicleInfo vehicleInfo(Integer vId) {
        if (vId == null){
            return new VehicleInfo();
        }
        VehicleInfo vehicle = vehicleInfoMapper.getVehicle(vId);
        return vehicle;
    }

    @Override
    public List<Double> ax(Integer aId) {
        if (aId == null){
            return new ArrayList<Double>();
        }
        return axMapper.getAx(aId);
    }

    @Override
    public List<Double> ay(Integer aId) {
        if (aId == null){
            return new ArrayList<Double>();
        }
        return ayMapper.getAy(aId);
    }

    @Override
    public void solveAccident(Integer aID) {
        if (aID == null){
            return;
        }
        accidentInfoMapper.updateState(aID);
    }

    @Override
    public List<TotalInfo> search(String search) {
        if (search == null) {
            return new ArrayList<TotalInfo>();
        }
        List<VehicleInfo> vehicles = null;
        //分析search是车牌号还是车主姓名
        //车主姓名
        if (search.contains("-"))//车牌号
            vehicles = vehicleInfoMapper.getVehicleByVNum(search);
        else
            vehicles = vehicleInfoMapper.getVehicleByVName(search);
        if (vehicles.size() <= 0){
            return null;
        }
        //根据vehicle信息查询accidentInfo
        Iterator<VehicleInfo> iterator1 = vehicles.iterator();
        List<TotalInfo> list = new ArrayList<>();
        while (iterator1.hasNext()) {
            VehicleInfo vehicle =  iterator1.next();
            List<AccidentInfo> accidentInfoByVId = accidentInfoMapper.getAccidentInfoByVId(vehicle.getId());
            //Integer vId, Integer aId, String vNum, String vName, String vType, String date, boolean status
            //封装totalInfo
            Iterator<AccidentInfo> iterator = accidentInfoByVId.iterator();
            while (iterator.hasNext()) {
                AccidentInfo accidentInfo = iterator.next();
                //处理日期,查询时间
                long l = DateFormatUtils.dateLong();
                String dateTime = DateFormatUtils.myFormatTime(l);
                String[] s = dateTime.split(" ");
                //处理状态
                TotalInfo totalInfo = new TotalInfo();
                if (accidentInfo.getState() == 0)
                    totalInfo = new TotalInfo(vehicle.getId(),accidentInfo.getId(),vehicle.getVNum(),vehicle.getVName(),vehicle.getVType(),s[0],false);
                else
                    totalInfo = new TotalInfo(vehicle.getId(),accidentInfo.getId(),vehicle.getVNum(),vehicle.getVName(),vehicle.getVType(),s[0],true);
                list.add(totalInfo);
            }
        }
        return list;
    }
}
