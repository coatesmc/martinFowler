package com.coates.helloservice.demo.upd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @ClassName udp
 * @Description TODO
 * @Author mc
 * @Date 2019/7/9 10:52
 * @Version 1.0
 **/
public class UdpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("udp 发送数据");
        DatagramSocket ds= new DatagramSocket();
        while (true){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             String str = br.readLine();
             byte[] strByte = str.getBytes();
            DatagramPacket dp = new DatagramPacket(strByte, strByte.length, InetAddress.getByName("127.0.0.1"),8080);
            ds.send(dp);
        }


    }
}
