package com.why.transportsecurity_finally.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleInfoMapperTest {

    @Autowired
    VehicleInfoMapper vehicleInfoMapper;

    @Test
    void getVehicleId() {
        Integer vehicleId = vehicleInfoMapper.getVehicleId("甘A-8888");
        System.out.println(vehicleId);
    }
}