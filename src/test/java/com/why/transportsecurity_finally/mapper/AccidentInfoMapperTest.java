package com.why.transportsecurity_finally.mapper;

import com.why.transportsecurity_finally.entity.AccidentInfo;
import com.why.transportsecurity_finally.utils.DateFormatUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccidentInfoMapperTest {

    @Autowired
    AccidentInfoMapper accidentInfoMapper;

    @Test
    void insertAccident() {
        //添加accidentInfo
        long time = DateFormatUtils.dateLong();
        AccidentInfo accidentInfoNew = new AccidentInfo(null, time, 123.456789, 25.123456, 0, 1);
        accidentInfoMapper.insertAccident(accidentInfoNew);
        System.out.println(accidentInfoNew.getId());
    }
}