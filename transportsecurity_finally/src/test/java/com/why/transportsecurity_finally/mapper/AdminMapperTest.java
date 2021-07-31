package com.why.transportsecurity_finally.mapper;

import com.why.transportsecurity_finally.entity.AdminInfo;
import com.why.transportsecurity_finally.utils.DateFormatUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminMapperTest {

    @Autowired
    AdminMapper adminMapper;

    @Test
    void updateAdminInfo() throws ParseException {
        //Integer id, String uPhone, String uEmail, String uAddress, Date uBirth, Date uDate, Integer uId
        String s = DateFormatUtils.myFormatTime(DateFormatUtils.dateLong());
        AdminInfo adminInfo = new AdminInfo(1, "15337086013", "488009667@qq.com", "江苏大学", s,s, 5);
        adminMapper.updateAdminInfo(adminInfo);
    }
}