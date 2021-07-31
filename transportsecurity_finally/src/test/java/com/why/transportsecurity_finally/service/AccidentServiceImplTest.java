package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.entity.TotalInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccidentServiceImplTest {

    @Autowired
    AccidentServiceImpl accidentService;

    @Test
    void search() {
        List<TotalInfo> search = accidentService.search("why");
        Iterator<TotalInfo> iterator = search.iterator();
        while (iterator.hasNext()) {
            TotalInfo next = iterator.next();
            System.out.println(next);
        }
    }
}