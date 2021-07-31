package com.why.transportsecurity_finally.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Description TODO 随机数工具类
 * @Author why
 * @Date 2021/7/28 11:53
 * Version 1.0
 **/
public class RandomUtils {

    public static String getRandom(){
        Date date = new Date();
        Random random = new Random(date.getTime());
        int i = random.nextInt(1000000);
        return i+"";
    }
}
