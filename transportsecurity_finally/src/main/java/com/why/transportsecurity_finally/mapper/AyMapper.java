package com.why.transportsecurity_finally.mapper;

import com.why.transportsecurity_finally.entity.Ay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description TODO ay加速度mapper
 * @Author why
 * @Date 2021/7/21 11:18
 * Version 1.0
 **/
@Mapper
public interface AyMapper {

    /**
     * 根据aID查询ay
     * @param aID
     * @return
     */
    public List<Double> getAy(Integer aID);

    /**
     * 插入ay
     * @param ay
     */
    public void insertAy(Ay ay);

    /**
     * 删除ax
     * @param aId
     */
    public void deleteAy(Integer aId);
}
