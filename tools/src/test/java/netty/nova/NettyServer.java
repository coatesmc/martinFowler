package netty.nova;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;


/**
 * @ClassName NettyServer
 * @Description TODO
 * @Author mc
 * @Date 2019/7/29 17:15
 * @Version 1.0
 **/
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup pGroup = new NioEventLoopGroup();
        NioEventLoopGroup cGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(pGroup,cGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,1024)
        .option(ChannelOption.SO_SNDBUF,32*1024).option(ChannelOption.SO_RCVBUF,32*1024).childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                sc.pipeline().addLast(new StringDecoder());
                sc.pipeline().addLast(new ServerHandler());
            }
        });
        ChannelFuture cf = serverBootstrap.bind(8080).sync();
        cf.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();
    }
}
