package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.entity.*;
import com.why.transportsecurity_finally.mapper.AccidentInfoMapper;
import com.why.transportsecurity_finally.mapper.AxMapper;
import com.why.transportsecurity_finally.mapper.AyMapper;
import com.why.transportsecurity_finally.mapper.VehicleInfoMapper;
import com.why.transportsecurity_finally.utils.AccidentUtils;
import com.why.transportsecurity_finally.utils.BaiduMapUtils;
import com.why.transportsecurity_finally.utils.DateFormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * @Description TODO 事故处理实现类
 * @Author why
 * @Date 2021/7/21 11:54
 * Version 1.0
 **/
@Transactional
@Service
@Slf4j
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
            long l = accidentInfo.getADate();
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
            long l = accidentInfo.getADate();
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
        long l = accidentInfoById.getADate();
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

    @Override
    public void insert(String info) {
        String[] infos = info.split("\n");

        //处理经纬度
        String[] lngStr = infos[1].split(" ");
        String[] latStr = infos[2].split(" ");
        double lng = Double.valueOf(lngStr[0].trim().substring(10));
        double lat = Double.valueOf(latStr[0].trim().substring(9));

        //处理车牌号
        String vNum = infos[4].trim().substring(4,11);

        //处理ax，ay
        String axStr = infos[4].trim().substring(14);
        String ayStr = infos[5].trim().substring(3);
        String[] axs = axStr.split(",");
        //处理ax
        List<Double> ax = new ArrayList<>();
        for (int i = 0; i < axs.length; i++) {
            ax.add(Double.valueOf(axs[i].trim()));
        }

        //处理ay
        String[] ays = ayStr.trim().split(",");
        List<Double> ay = new ArrayList<>();
        for (int i = 0; i < ays.length ; i++) {
            ay.add(Double.valueOf(ays[i].trim()));
        }

        //查出车辆id
        Integer vehicleId = vehicleInfoMapper.getVehicleId(vNum);
        if (vehicleId != null){
            //添加accidentInfo
            long time = DateFormatUtils.dateLong();
            AccidentInfo accidentInfoNew = new AccidentInfo(null, time, lng, lat, 0, vehicleId);
            accidentInfoMapper.insertAccident(accidentInfoNew);
            Integer aId = accidentInfoNew.getId();
            //添加ax
            Iterator<Double> iteratorAx = ax.iterator();
            while (iteratorAx.hasNext()) {
                Double x = iteratorAx.next();
                Ax ax1 = new Ax(null, x, aId);
                axMapper.insertAx(ax1);
            }
            //添加ay
            Iterator<Double> iteratorAy = ay.iterator();
            while (iteratorAy.hasNext()) {
                Double y =  iteratorAy.next();
                Ay ay1 = new Ay(null, y, aId);
                ayMapper.insertAy(ay1);
            }
        }else {
            log.error("该车辆未注册！");
            return;
        }
    }
}
