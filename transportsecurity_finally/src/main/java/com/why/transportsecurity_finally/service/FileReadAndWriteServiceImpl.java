package com.why.transportsecurity_finally.service;

import com.why.transportsecurity_finally.mapper.AxMapper;
import com.why.transportsecurity_finally.mapper.AyMapper;
import com.why.transportsecurity_finally.entity.Ax;
import com.why.transportsecurity_finally.entity.Ay;
import com.why.transportsecurity_finally.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description TODO 文件读写实现类
 * @Author why
 * @Date 2021/7/22 15:02
 * Version 1.0
 **/
@Service
public class FileReadAndWriteServiceImpl implements FileReadAndWriteService {

    @Autowired
    AxMapper axMapper;
    @Autowired
    AyMapper ayMapper;

    @Override
    public void writeAx() {
        List<List<String>> lists = ExcelUtils.readExcel("E:\\车辆安全\\tranprotsecurity-latest\\tranportsecurity_latest\\src\\main\\resources\\testFile\\ax.xlsx");
        Iterator<List<String>> iterator = lists.iterator();
        List<Object> list = new ArrayList<>();
        while (iterator.hasNext()) {
            List<String> next = iterator.next();
            list.add(next.get(0));
        }

        list.remove(0);
        List<Ax> ax = new ArrayList<>();
        Iterator<Object> iterator1 = list.iterator();
        int count = 300;
        while (iterator1.hasNext()) {
            count++;
            String next = (String) iterator1.next();
            ax.add(new Ax(count, Double.valueOf(next),2));
        }
        Iterator<Ax> iterator2 = ax.iterator();
        while (iterator2.hasNext()) {
            Ax next = iterator2.next();
            axMapper.insertAx(next);
        }

    }

    @Override
    public void writeAy() {
        List<List<String>> lists = ExcelUtils.readExcel("E:\\车辆安全\\tranprotsecurity-latest\\tranportsecurity_latest\\src\\main\\resources\\testFile\\ay.xlsx");
        Iterator<List<String>> iterator = lists.iterator();
        List<Object> list = new ArrayList<>();
        while (iterator.hasNext()) {
            List<String> next = iterator.next();
            list.add(next.get(0));
        }

        list.remove(0);
        List<Ay> ay = new ArrayList<>();
        Iterator<Object> iterator1 = list.iterator();
        int count = 200;
        while (iterator1.hasNext()) {
            count++;
            String next = (String) iterator1.next();
            ay.add(new Ay(count, Double.valueOf(next),2));
        }
        Iterator<Ay> iterator2 = ay.iterator();
        while (iterator2.hasNext()) {
            Ay next = iterator2.next();
            ayMapper.insertAy(next);
        }

    }
}
