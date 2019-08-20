package netty.outdated;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NettyClinet
 * @Description TODO
 * @Author mc
 * @Date 2019/7/29 16:06
 * @Version 1.0
 **/
public class NettyClinet {
    public static void main(String[] args) {
        // 创建服务类对象
        ClientBootstrap clientBootstrap = new ClientBootstrap();
        // 线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService wook = Executors.newCachedThreadPool();
        // 设置工程 并把两个线程池加入中
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(boss, wook));
        // 设置管道工厂
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("clientHanlder", new ClientHanlder());
                return pipeline;
            }
        });
        //绑定端口号
        ChannelFuture connect = clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("客户端已经启动。。。。。");
        Channel channel = connect.getChannel();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入内容:");
            channel.write(scanner.next());
        }
    }
}

/**
 * 事件监听
 */
class ClientHanlder extends SimpleChannelHandler {
    //通道呗关闭时触发
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelClosed(ctx, e);
        System.out.println("channelClosed");
    }

    //必须建立连接，关闭通道时才会被触发
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        System.out.println("exceptionCaught" + e.getState());
    }

    //接受出现的异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
        System.out.println("exceptionCaught" + ctx.getAttachment());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
        System.out.println("exceptionCaught");
        System.out.println("服务器端发来信息:" + e.getMessage());
    }
}