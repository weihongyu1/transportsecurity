package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.entity.AdminAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    AdminServiceImpl adminService;

    @Test
    void getAdminInfo() {
        AdminAll adminInfo = adminService.getAdminInfo(1);
        System.out.println(adminInfo);
    }
}