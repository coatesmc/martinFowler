package netty.outdated;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NettyServer
 * @Description TODO
 * @Author mc
 * @Date 2019/7/29 15:47
 * @Version 1.0
 **/
public class NettyServer {
    public static void main(String[] args) {
        // 创建服务类对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //创建两个线程池 分别为监听监听端口 ，nio监听
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService wook = Executors.newCachedThreadPool();
        // 设置工程 并把两个线程池加入中
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss, wook));
        // 设置管道工厂
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                //将数据转换为string类型.
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("serverhanlder", new ServerHanlder());
                return pipeline;
            }
        });
        //绑定端口号
        serverBootstrap.bind(new InetSocketAddress(8080));
        System.out.println("netty server启动....");
    }
}

/**
 * 事件监听
 */
class ServerHanlder extends SimpleChannelHandler {
    /**
     * 通道呗关闭时触发
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
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
        System.out.println("客户端发来信息:" + e.getMessage());
        ctx.getChannel().write("来了老弟！");
    }
}

