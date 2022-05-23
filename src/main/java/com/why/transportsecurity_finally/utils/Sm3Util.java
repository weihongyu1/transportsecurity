package com.why.transportsecurity_finally.utils;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Description TODO SM3哈希算法
 * @Author why
 * @Date 2021/6/15 13:44
 * Version 1.0
 **/
public class Sm3Util {
    public static String addSalt(String str,String salt){
        String newStr = str + salt;

        BouncyCastleProvider provider = new BouncyCastleProvider();
        try {
            MessageDigest digest = MessageDigest.getInstance("SM3",provider);
            byte[] encode = Hex.encode(digest.digest(newStr.getBytes(StandardCharsets.UTF_8)));
            String s = new String(encode);
            return s;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String();
    }

    /**
     * 随机生成10位字符串返回
     * @return
     */
    public static String creatSalt() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }

    /**
     * 对字符串进行hash
     * @param str
     * @return
     */
    public static String hashStr(String str){
        BouncyCastleProvider provider = new BouncyCastleProvider();
        try {
            MessageDigest digest = MessageDigest.getInstance("SM3",provider);
            byte[] encode = Hex.encode(digest.digest(str.getBytes(StandardCharsets.UTF_8)));
            String s = new String(encode);
            return s;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String();
    }
}
