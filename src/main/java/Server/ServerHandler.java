package Server;

import Hibernate.ManageEmployee;
import entity.Employee;
import io.netty.channel.*;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
        public void handlerAdded(ChannelHandlerContext ctx){
        Channel incoming = ctx.channel();
        System.out.println("[SERVER] - " + incoming.remoteAddress() + " has joined\n");
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        Channel incoming = ctx.channel();
        System.out.println("[SERVER] - " + incoming.remoteAddress() + " has left\n");
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String arg1){
        if(arg1.contains("list")){
            ManageEmployee manageEmployee = new ManageEmployee();
            manageEmployee.listEmployees(ctx);
        }
        else if(arg1.contains("create")){
            String[] dataEmployee = arg1.split("\\s+");
            ManageEmployee manageEmployee = new ManageEmployee();
            int idEmployee = manageEmployee.addEmployee(dataEmployee[1], dataEmployee[2], Integer.parseInt(dataEmployee[3]));
            ctx.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + idEmployee + '\n');
        }
        else if(arg1.contains("delete")){
            String[] dataEmployee = arg1.split("\\s+");
            ManageEmployee manageEmployee = new ManageEmployee();
            manageEmployee.deleteEmployee(Integer.parseInt(dataEmployee[1]));
            ctx.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + "remove SuccessFully" + '\n');
        }
        else{
            ctx.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + arg1 + '\n');
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}