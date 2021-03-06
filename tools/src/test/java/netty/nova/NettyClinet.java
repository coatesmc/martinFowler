package netty.nova;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @ClassName NettyClinet
 * @Description TODO
 * @Author mc
 * @Date 2019/7/30 9:29
 * @Version 1.0
 **/
public class NettyClinet {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("客户端已经启动....");
        // 创建负责接收客户端连接
        NioEventLoopGroup pGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(pGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                sc.pipeline().addLast(new StringDecoder());
                sc.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture cf = b.connect("127.0.0.1", 8080).sync();
        cf.channel().writeAndFlush(Unpooled.wrappedBuffer("滚犊子".getBytes()));
        cf.channel().writeAndFlush(Unpooled.wrappedBuffer("itmayiedu".getBytes()));
        // 等待客户端端口号关闭
        cf.channel().closeFuture().sync();
        pGroup.shutdownGracefully();

    }

}
