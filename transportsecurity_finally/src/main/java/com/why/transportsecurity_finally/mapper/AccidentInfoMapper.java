package com.why.transportsecurity_finally.mapper;

import com.why.transportsecurity_finally.entity.AccidentInfo;
import com.why.transportsecurity_finally.entity.VehicleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description TODO 事故信息mapper
 * @Author why
 * @Date 2021/7/21 10:52
 * Version 1.0
 **/
@Mapper
public interface AccidentInfoMapper {
    /**
     * 根据车辆事故信息id查询事故信息
     * @param id 事故信息id
     * @return
     */
    public AccidentInfo getAccidentInfoById(Integer id);

    /**
     * 根据车辆基本信息id，查询车辆事故信息
     * @param vId 车辆基本信息id
     * @return
     */
    public List<AccidentInfo> getAccidentInfoByVId(Integer vId);

    /**
     * 获得所有已处理数据
     * @return
     */
    public List<AccidentInfo> getAllAccidentInfoSolve();

    /**
     * 获得所有未处理数据
     * @return
     */
    public List<AccidentInfo> getAllAccidentInfoUnSolve();

    /**
     * 更新处理状态
     * @param aID
     */
    public void updateState(Integer aID);

    public void deleteAccident(Integer vId);
}
