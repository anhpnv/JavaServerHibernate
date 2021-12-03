package Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg){
        System.out.println(msg);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable msg){
        msg.printStackTrace();
        ctx.close();
    }
}

