package com.why.transportsecurity_finally.utils.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description TODO
 * @Author why
 * @Date 2021/7/31 15:10
 * Version 1.0
 **/
public class Server {
    public static void main(String[] args) {
        System.out.println("服务端启动. . .");
        ServerSocket server = null;
        Integer port = 8888;
        try {
            server = new ServerSocket(port);
            while (true){
                //获取一个客户端连接
                Socket accept = server.accept();
                //创建信息线程处理客户端信息
                new Thread(() -> {
                    InputStream is = null;
                    InputStreamReader reader = null;
                    BufferedReader br = null;
                    try {
                        //获取字节输入流
                        is = accept.getInputStream();
                        //将字节输入流转为字符输入流
                        reader = new InputStreamReader(is);
                        //将字符输入流包装成缓冲字符输入流
                        br = new BufferedReader(reader);
                        //循环读取消息
                        String msg = "";
                        while ((msg=br.readLine())!=null){
                            System.out.println(accept.getRemoteSocketAddress()+": "+msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if (is != null){
                                is.close();
                            }
                            if (reader != null){
                                reader.close();
                            }
                            if (br != null){
                                br.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
