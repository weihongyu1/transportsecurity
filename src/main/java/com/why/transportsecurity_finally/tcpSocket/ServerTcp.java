package com.why.transportsecurity_finally.tcpSocket;

import com.why.transportsecurity_finally.service.AccidentServiceImpl;
import com.why.transportsecurity_finally.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @ClassName：ServerTcp
 * @Description：todo 接收客户端
 * @Author: why
 * @DateTime：2021/8/26 17:24
 */
@Slf4j
public class ServerTcp extends Thread {

    //创建Socket对象
    private Socket accept;

    /**
     * 初始化serverSocket
      * @param serverSocket
     */
    public ServerTcp(Socket serverSocket) {
        this.accept = serverSocket;
    }

    /**
     * 获取数据
     * @param is
     * @return
     */
    private String handle(InputStream is, ByteArrayOutputStream baos) throws IOException {
        log.info("处理");
        byte[] buffer = new byte[1024 * 1024 * 10];
        int len = 0;
       while ((len = is.read(buffer)) != -1){
           baos.write(buffer,0,len);
           if (baos.toString().substring(baos.toString().length()-3).equals("end")){
               break;
           }
       }
       log.info("数据接收完毕");
       return baos.toString();
    }

    @Override
    public void run() {
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            log.info(accept.getRemoteSocketAddress() + " -- " + "建立连接");
            //监听
            is = accept.getInputStream();
            baos = new ByteArrayOutputStream();
            //处理接收数据
            log.info(accept.getRemoteSocketAddress() + " -- " + "开始传输数据");
            String info = handle(is, baos);
            log.info("数据：{}", info);
            //获取service
            AccidentServiceImpl accidentService = SpringUtils.getBean(AccidentServiceImpl.class);
            log.info(accept.getRemoteSocketAddress() + " -- " + "数据正在写入数据库");
            if (!info.contains("车牌号：") && info.contains("ax:") && info.contains("ay:")){
                log.error(accept.getRemoteSocketAddress()+" - 发送数据错误");
                return;
            }
            accidentService.insert(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
