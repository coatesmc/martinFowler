package netty.nova;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName ServerHandler
 * @Description TODO
 * @Author mc
 * @Date 2019/7/29 17:24
 * @Version 1.0
 **/
public class ServerHandler extends ChannelHandlerAdapter {

    /**
     *  通道被调用时会被执行
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        String str = (String) msg;
        System.out.println("Server msg:"+str);
        String res = "了解";
        ctx.writeAndFlush(Unpooled.copiedBuffer(res.getBytes()));
    }
}
