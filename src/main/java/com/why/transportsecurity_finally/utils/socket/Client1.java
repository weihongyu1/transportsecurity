package com.why.transportsecurity_finally.utils.socket;

import lombok.extern.java.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @Description TODO
 * @Author why
 * @Date 2021/7/31 15:35
 * Version 1.0
 **/
@Log
public class Client1 {
    public static void main(String[] args) {
        log.info("客户端启动...");
        Socket socket = null;
        OutputStream os = null;
        try {
            //创建socket实例，绑定端口号和ip
            socket = new Socket(InetAddress.getByName("127.0.0.1"),8888);
            for (int i = 0; i < 2; i++) {
                //创建输出流，准备发送数据
                os = socket.getOutputStream();
                Scanner scanner = new Scanner(System.in);
                System.out.println("输入：");
                String s = scanner.nextLine();
                os.write(s.getBytes());
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //资源关闭
            try {
                if (os != null){
                    os.close();
                }
                if (socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
