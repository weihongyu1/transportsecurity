package com.why.transportsecurity_finally.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileReadAndWriteServiceImplTest {

    @Autowired
    private FileReadAndWriteServiceImpl fileReadAndWriteService;

    @Test
    void writeAx() {
        fileReadAndWriteService.writeAx();
    }

    @Test
    void writeAy() {
        fileReadAndWriteService.writeAy();
    }
}
