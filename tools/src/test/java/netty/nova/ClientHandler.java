package netty.nova;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @ClassName ClientHandler
 * @Description TODO
 * @Author mc
 * @Date 2019/7/30 9:37
 * @Version 1.0
 **/
public class ClientHandler extends ChannelHandlerAdapter {
    /**
     * 当通道被调用,执行该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        // 接收数据
        String value = (String) msg;
        System.out.println("client msg:" + value);
    }
}
