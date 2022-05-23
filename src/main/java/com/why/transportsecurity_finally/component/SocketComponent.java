package com.why.transportsecurity_finally.component;

import com.why.transportsecurity_finally.config.SocketConfig;
import com.why.transportsecurity_finally.tcpSocket.ServerTcp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName：SocketComponent
 * @Description：todo 让Socket程序跟随Springboot一起启动
 * @Author: why
 * @DateTime：2021/8/26 17:55
 */
@Component
@Slf4j
public class SocketComponent implements CommandLineRunner {

    @Autowired
    private SocketConfig socketConfig;

    @Override
    public void run(String... args) throws Exception {
        ServerSocket server = null;
        Socket socket = null;
        server = new ServerSocket(socketConfig.getPort());
        log.info("设备服务器已启动，监听端口："+ socketConfig.getPort());
        //创建线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                socketConfig.getPoolCore(),
                socketConfig.getPoolMax(),
                socketConfig.getPoolKeep(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(socketConfig.getPoolQueueInit()),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        //循环监听
        while (true){
            socket = server.accept();
            pool.execute(new ServerTcp(socket));
        }
    }
}
