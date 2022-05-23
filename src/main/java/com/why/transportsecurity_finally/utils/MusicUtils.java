package com.why.transportsecurity_finally.utils;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @Description TODO 音乐播放工具类
 * @Author why
 * @Date 2021/7/24 11:06
 * Version 1.0
 **/
public class MusicUtils {
    //声明一个全局的player对象
    public static Player player = null;
    public static void mp3Player(String path) {
        try {
            //声明一个File对象
            File mp3 = new File(path);
            //创建一个输入流
            FileInputStream fileInputStream = new FileInputStream(mp3);
            //创建一个缓冲流
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            //创建播放器对象，把文件的缓冲流传入进去
            player = new Player(bufferedInputStream);
            //调用播放方法进行播放
            player.play();
            player.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MusicUtils.mp3Player("src\\main\\resources\\static\\music\\warning.mp3");
    }
}
