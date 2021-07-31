package com.why.transportsecurity_finally.mapper;


import com.why.transportsecurity_finally.entity.Ax;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description TODO ax加速度mapper
 * @Author why
 * @Date 2021/7/21 11:11
 * Version 1.0
 **/
@Mapper
public interface AxMapper {
    /**
     * 根据a_id查询加速度ax
     * @param aId
     * @return
     */
    public List<Double> getAx(Integer aId);

    /**
     * 插入ax加速度
     * @param ax
     */
    public void insertAx(Ax ax);

    /**
     * 删除ax
     * @param aId
     */
    public void deleteAx(Integer aId);
}
