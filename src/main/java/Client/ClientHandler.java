package Client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;
    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        buf = ctx.alloc().buffer(4);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        buf.release();
        buf = null;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        buf.writeBytes(in);
        in.release();
        if(buf.readableBytes() >= 4){
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println("In ChannelRead");
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable msg){
        msg.printStackTrace();
        ctx.close();
    }
}

