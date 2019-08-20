package com.coates.helloservice.demo.upd;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLOutput;

/**
 * @ClassName UDPService
 * @Description TODO
 * @Author mc
 * @Date 2019/7/9 10:52
 * @Version 1.0
 **/

public class UDPService {

    public static void main(String[] args) throws IOException {
        System.out.println("udp 服务端启动");
        DatagramSocket ds= new DatagramSocket(8080);
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        while (true){
            ds.receive(dp);
            System.out.println("来源:"+dp.getAddress().getHostAddress()+"port:"+dp.getPort());
            String str = new String(dp.getData(),0,dp.getLength());
            System.out.println("客户端发送数据:"+str);
        }
    }
}
