package com.coates.registrationcenter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @ClassName TcpServer
 * @Description TODO
 * @Author mc
 * @Date 2019/7/29 15:01
 * @Version 1.0
 **/
public class TcpServer {

   /* public static void main(String[] args) throws IOException {
        System.out.println("服务器端启用。。。。。");
        //创建通道
        ServerSocketChannel sChannel = ServerSocketChannel.open();
        //切换读取模式
        sChannel.configureBlocking(false);
        // 绑定连接
        sChannel.bind(new InetSocketAddress(8080));

        //选择器
        Selector selector = Selector.open();
        //将通道注册到选择器 "并且指定监听接受事件"
        sChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 轮训式 获取选择 "已经准备就绪"的事件
        while (selector.select() > 0) {
            // 获取当前选择器所有注册的"选择键(已经就绪的监听事件)"
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                // 获取准备就绪的事件
                SelectionKey sk = it.next();
                // 判断具体是什么事件准备就绪
                if (sk.isAcceptable()) {
                    //若"接受就绪",获取客户端连接
                    SocketChannel accept = sChannel.accept();
                    // 设置阻塞模式
                    accept.configureBlocking(false);
                    // 将该通道注册到服务器上
                    accept.register(selector, SelectionKey.OP_READ);

                } else if (sk.isReadable()) {
                    //获取当前选择器"就绪" 状态的通道
                    SocketChannel channel = (SocketChannel) sk.channel();
                    // 读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    len = channel.read(byteBuffer);
                    while (len > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
                it.remove();
            }
        }
    }*/


}
